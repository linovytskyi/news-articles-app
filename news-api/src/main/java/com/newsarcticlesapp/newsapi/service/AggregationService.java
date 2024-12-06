package com.newsarcticlesapp.newsapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newsarcticlesapp.newsapi.client.ClassificationClient;
import com.newsarcticlesapp.newsapi.client.SummarizationClient;
import com.newsarcticlesapp.newsapi.client.model.ClassificationResponse;
import com.newsarcticlesapp.newsapi.client.model.SummarizationResponse;
import com.newsarcticlesapp.newsapi.client.model.ArticleText;
import com.newsarcticlesapp.newsapi.listener.model.AggregatedArticle;
import com.newsarcticlesapp.newsapi.model.*;
import com.newsarcticlesapp.newsapi.repository.ArticleRepository;
import com.newsarcticlesapp.newsapi.repository.SourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AggregationService {

    private final ObjectMapper objectMapper;
    private final ClassificationClient classificationClient;
    private final SummarizationClient summarizationClient;
    private final SourceRepository sourceRepository;
    private final ArticleService articleService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AggregationService.class);
    private final ArticleRepository articleRepository;

    private final List<String> LIST_OF_TOPICS_TO_GENERATE_TITLE = List.of("Війна", "Економіка", "Енергетика", "Політика", "Україна", "Світ");

    public AggregationService(ObjectMapper objectMapper,
                              ClassificationClient classificationClient,
                              SummarizationClient summarizationClient,
                              SourceRepository sourceRepository,
                              ArticleService articleService, ArticleRepository articleRepository) {
        this.objectMapper = objectMapper;
        this.classificationClient = classificationClient;
        this.summarizationClient = summarizationClient;
        this.sourceRepository = sourceRepository;
        this.articleService = articleService;
        this.articleRepository = articleRepository;
    }

    @Transactional
    public void processAggregatedArticleFromQueue(String message) {
        try {
            AggregatedArticle aggregatedArticle = objectMapper.readValue(message, AggregatedArticle.class);
            LOGGER.info("Processing aggregated article {}", aggregatedArticle);

            Article article = Article.createBaseArticleFromAggregated(aggregatedArticle);
            processArticleSource(aggregatedArticle.getSourceUrl(), article);

            Optional<Article> duplicateArticle = articleService.findArticleByOriginalTitleAndSource(article.getOriginalTitle(), article.getSource());

            if (duplicateArticle.isEmpty()) {
                ArticleText text = new ArticleText(aggregatedArticle.getText());

                processArticleClassification(text, article);
                processArticleSummarization(text, article);

                LOGGER.info("Processed article {}", article);
                articleService.saveArticle(article);
            } else {
                LOGGER.info("Duplicate article ignored {}", article);
            }

        } catch (Exception e) {
            LOGGER.error("Exception occurred: {}", Arrays.toString(e.getStackTrace()));
        }
    }

    private void processArticleClassification(ArticleText text, Article article) {
        ClassificationResponse response = classificationClient.getClassification(text);
        article.setTopic(response.getTopic().getName());
        article.setKeywords(getKeywordsSetFromClassificationResponse(response));
    }

    private void processArticleSummarization(ArticleText text, Article article) {
        SummarizationResponse response = summarizationClient.getSummarization(text);

        if (LIST_OF_TOPICS_TO_GENERATE_TITLE.contains(article.getTopic())) {
            article.setTitle(response.getTitle());
        }
        article.setSummary(response.getSummary());
    }

    private void processArticleSource(String sourceUrl, Article article) {
        Optional<Source> source = sourceRepository.findByUrl(sourceUrl);
        if (source.isPresent()) {
            article.setSource(source.get());
        } else {
            throw new IllegalArgumentException(String.format("Unknown source: %s", sourceUrl));
        }
    }

    private Set<ArticleKeyword> getKeywordsSetFromClassificationResponse(ClassificationResponse response) {
        List<String> persons = response.getPersons();
        Set<ArticleKeyword> keywordSet = new HashSet<>(getSetOfKeywordsWithType(persons, KeywordType.PERSON));

        List<String> locations = response.getLocations();
        keywordSet.addAll(getSetOfKeywordsWithType(locations, KeywordType.LOCATION));

        List<String> organizations = response.getOrganizations();
        keywordSet.addAll(getSetOfKeywordsWithType(organizations, KeywordType.ORGANIZATION));

        List<String> general = response.getKeywords();
        keywordSet.addAll(getSetOfKeywordsWithType(general, KeywordType.GENERAL));

        return keywordSet;
    }

    private Set<ArticleKeyword> getSetOfKeywordsWithType(List<String> values, KeywordType type) {
        Set<ArticleKeyword> keywordSet = new HashSet<>();
        for (String value : values) {
            keywordSet.add(new ArticleKeyword(value, type));
        }
        return keywordSet;
    }
}

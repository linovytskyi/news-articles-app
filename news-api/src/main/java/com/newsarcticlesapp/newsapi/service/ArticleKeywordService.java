package com.newsarcticlesapp.newsapi.service;

import com.newsarcticlesapp.newsapi.model.ArticleKeyword;
import com.newsarcticlesapp.newsapi.model.Source;
import com.newsarcticlesapp.newsapi.model.analytics.KeywordCount;
import com.newsarcticlesapp.newsapi.repository.ArticleKeywordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArticleKeywordService {

    private final ArticleKeywordRepository articleKeywordRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleKeywordService.class);
    private static final Integer DEFAULT_AMOUNT_OF_KEYWORDS = 10;
    private static final String KEYWORD_KEY = "keyword";
    private static final String KEYWORD_COUNT = "keywordCount";

    public ArticleKeywordService(ArticleKeywordRepository articleKeywordRepository) {
        this.articleKeywordRepository = articleKeywordRepository;
    }

    public List<String> getTopKeywords(Integer amount) {
        return getTopKeywordsForAmount(amount);
    }

    public List<KeywordCount> getTopKeywordsCount(Integer amount) {
        return getTopKeywordsCountsForAmount(amount);
    }

    public List<KeywordCount> getTopKeywordsCountForTopic(String topic, Integer amount) {
        return getTopKeywordsCountForTopicAndAmount(topic, amount);
    }

    public List<KeywordCount> getTopKeywordsCountForSource(Source source, Integer amount) {
        return getTopKeywordsCountForSourceAndAmount(source, amount);
    }

    public List<String> getTopKeywordsForTopic(String topic, Integer amount) {
        return getTopKeywordsForTopicAndAmount(topic, amount);
    }

    private List<String> getTopKeywordsForAmount(Integer amount) {
        if (amount == null) {
            amount = DEFAULT_AMOUNT_OF_KEYWORDS;
        }
        LOGGER.info("Getting top {} keywords for articles on all topics", amount);
        List<Map<String, Object>> listOfKeywordCountMaps = articleKeywordRepository.findTopKeywords( PageRequest.of(0, amount));
        return mapListOfKeywordsCountMapToKeywordList(listOfKeywordCountMaps);
    }

    private List<KeywordCount> getTopKeywordsCountsForAmount(Integer amount) {
        if (amount == null) {
            amount = DEFAULT_AMOUNT_OF_KEYWORDS;
        }
        LOGGER.info("Getting top {} keywords for articles on all topics", amount);
        List<Map<String, Object>> listOfKeywordCountMaps = articleKeywordRepository.findTopKeywords( PageRequest.of(0, amount));
        return mapListOfKeywordsCountMapToKeywordCountList(listOfKeywordCountMaps);
    }

    private List<KeywordCount> getTopKeywordsCountForTopicAndAmount(String topic, Integer amount) {
        if (amount == null) {
            amount = DEFAULT_AMOUNT_OF_KEYWORDS;
        }
        LOGGER.info("Getting top {} keywords for articles on all topics", amount);
        List<Map<String, Object>> listOfKeywordCountMaps = articleKeywordRepository.findTopKeywordsForTopic(topic, PageRequest.of(0, amount));
        return mapListOfKeywordsCountMapToKeywordCountList(listOfKeywordCountMaps);
    }

    private List<KeywordCount> getTopKeywordsCountForSourceAndAmount(Source source, Integer amount) {
        if (amount == null) {
            amount = DEFAULT_AMOUNT_OF_KEYWORDS;
        }

        List<Map<String, Object>> listOfKeywordCountMaps = articleKeywordRepository.findTopKeywordsForSource(source.getId(), PageRequest.of(0, amount));
        return mapListOfKeywordsCountMapToKeywordCountList(listOfKeywordCountMaps);
    }

    private List<String> getTopKeywordsForTopicAndAmount(String topic, Integer amount) {
        if (amount == null) {
            amount = DEFAULT_AMOUNT_OF_KEYWORDS;
        }
        LOGGER.info("Getting top {} keywords for articles on topic {}", amount, topic);
        List<Map<String, Object>> listOfKeywordCountMaps = articleKeywordRepository.findTopKeywordsForTopic(topic, PageRequest.of(0, amount));
        return mapListOfKeywordsCountMapToKeywordList(listOfKeywordCountMaps);
    }

    private List<String> mapListOfKeywordsCountMapToKeywordList(List<Map<String, Object>> listOfKeywordCountMaps) {
        List<String> keywords = new ArrayList<>();

        for (Map<String, Object> keywordCountMap : listOfKeywordCountMaps) {
            String keyword = (String) keywordCountMap.get(KEYWORD_KEY);
            keywords.add(keyword);
        }

        LOGGER.info("List of keywords {}", keywords);
        return keywords;
    }

    public List<String> getArticleKeywordsAsListSet(Long articleId) {
        Set<ArticleKeyword> articleKeywords = getArticleKeywords(articleId);
        return mapArticleKeywordSetToValueStringList(articleKeywords);
    }

    private List<String> mapArticleKeywordSetToValueStringList(Set<ArticleKeyword> articleKeywords) {
        return articleKeywords.stream()
                .map(ArticleKeyword::getValue)
                .collect(Collectors.toList());
    }

    private List<KeywordCount> mapListOfKeywordsCountMapToKeywordCountList(List<Map<String, Object>> listOfKeywordCountMaps) {
        List<KeywordCount> keywordsCounts = new ArrayList<>();

        for (Map<String, Object> keywordCountMap : listOfKeywordCountMaps) {
            String keyword = (String) keywordCountMap.get(KEYWORD_KEY);
            Long count = (Long) keywordCountMap.get(KEYWORD_COUNT);
            keywordsCounts.add(new KeywordCount(keyword, count));
        }

        LOGGER.info("List of keywords counts {}", keywordsCounts);
        return keywordsCounts;
    }

    public Set<ArticleKeyword> getArticleKeywords(Long articleId) {
        LOGGER.info("Getting article keywords for article with id {}", articleId);
        Set<ArticleKeyword> articleKeywords = articleKeywordRepository.findAllByArticleId(articleId);
        LOGGER.info("Found {} article keywords", articleKeywords.size());
        return articleKeywords;
    }

    public long countAmountOfArticlesWithKeyword(String keyword) {
        return articleKeywordRepository.countArticlesWithKeywordValue(keyword);
    }
}

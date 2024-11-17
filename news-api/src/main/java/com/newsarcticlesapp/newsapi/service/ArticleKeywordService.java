package com.newsarcticlesapp.newsapi.service;

import com.newsarcticlesapp.newsapi.model.ArticleKeyword;
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

    public ArticleKeywordService(ArticleKeywordRepository articleKeywordRepository) {
        this.articleKeywordRepository = articleKeywordRepository;
    }

    public List<String> getTopKeywords(Integer amount) {
        return getTopKeywordsForAmount(amount);
    }

    public List<String> getTopKeywords(String topic, Integer amount) {
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

    public Set<ArticleKeyword> getArticleKeywords(Long articleId) {
        LOGGER.info("Getting article keywords for article with id {}", articleId);
        Set<ArticleKeyword> articleKeywords = articleKeywordRepository.findAllByArticleId(articleId);
        LOGGER.info("Found {} article keywords", articleKeywords.size());
        return articleKeywords;
    }
}

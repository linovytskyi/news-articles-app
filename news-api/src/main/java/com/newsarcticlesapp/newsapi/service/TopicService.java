package com.newsarcticlesapp.newsapi.service;

import com.newsarcticlesapp.newsapi.model.Article;
import com.newsarcticlesapp.newsapi.model.Source;
import com.newsarcticlesapp.newsapi.model.analytics.TopicsCount;
import com.newsarcticlesapp.newsapi.repository.ArticleKeywordRepository;
import com.newsarcticlesapp.newsapi.repository.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TopicService {

    private final ArticleRepository articleRepository;
    private static final Integer DEFAULT_AMOUNT_OF_TOP_TOPICS = 5;
    private static final String TOPIC_KEY = "topic";
    private static final String TOPIC_COUNT_KEY = "topicCount";
    private static final Logger LOGGER = LoggerFactory.getLogger(TopicService.class);
    private final ArticleKeywordRepository articleKeywordRepository;

    public TopicService(ArticleRepository articleRepository,
                        ArticleKeywordRepository articleKeywordRepository) {
        this.articleRepository = articleRepository;
        this.articleKeywordRepository = articleKeywordRepository;
    }

    public List<String> getAllTopics() {
        return this.articleRepository.findAllTopics();
    }

    public List<TopicsCount> getTopTopicsWithCount() {
        List<Map<String, Object>> listOfTopicsCountMaps = articleRepository.findTopTopics(PageRequest.of(0, DEFAULT_AMOUNT_OF_TOP_TOPICS));
        return mapListOfTopicsCountMapToTopicsCountList(listOfTopicsCountMaps);
    }

    public List<TopicsCount> getTopTopicsForSource(Source source) {
        List<Map<String, Object>> listOfTopicsCountMaps = articleRepository.findTopTopicsForSource(source.getId(), PageRequest.of(0, 5));
        return mapListOfTopicsCountMapToTopicsCountList(listOfTopicsCountMaps);
    }

    public List<TopicsCount> getTopTopicsWithKeyword(String keyword) {
        List<Map<String, Object>> listOfTopicsCountMaps = articleKeywordRepository.findTopTopicsForKeywordValue(keyword, PageRequest.of(0, DEFAULT_AMOUNT_OF_TOP_TOPICS));
        return mapListOfTopicsCountMapToTopicsCountList(listOfTopicsCountMaps);
    }

    private List<TopicsCount> mapListOfTopicsCountMapToTopicsCountList(List<Map<String, Object>> listOfTopicsCountMaps) {
        List<TopicsCount> topics = new ArrayList<>();

        for (Map<String, Object> keywordCountMap : listOfTopicsCountMaps) {
            String keyword = (String) keywordCountMap.get(TOPIC_KEY);
            Long count = (Long) keywordCountMap.get(TOPIC_COUNT_KEY);
            topics.add(new TopicsCount(keyword, count));
        }

        LOGGER.info("List of topics counts {}", topics);
        return topics;
    }
}

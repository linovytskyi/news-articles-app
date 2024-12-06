package com.newsarcticlesapp.newsapi.service;

import com.newsarcticlesapp.newsapi.model.analytics.TopicAnalytics;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsService {

    private final ArticleService articleService;
    private final ArticleKeywordService articleKeywordService;

    public AnalyticsService(ArticleService articleService,
                            ArticleKeywordService articleKeywordService) {
        this.articleService = articleService;
        this.articleKeywordService = articleKeywordService;
    }


    public TopicAnalytics getTopicAnalytics(String topicName) {
        TopicAnalytics topicAnalytics = new TopicAnalytics();
        topicAnalytics.setTopic(topicName);

        topicAnalytics.setAmountOfArticles(articleService.getAmountOfArticlesOnTopic(topicName));
        topicAnalytics.setMostPopularKeywords(articleKeywordService.getTopKeywordsCount(topicName, 30));
        topicAnalytics.setMostPopularSources(articleService.getMostPopularSources(topicName, 10));
        return topicAnalytics;
    }
}

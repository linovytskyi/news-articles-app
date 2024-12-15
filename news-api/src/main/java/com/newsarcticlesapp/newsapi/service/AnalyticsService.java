package com.newsarcticlesapp.newsapi.service;

import com.newsarcticlesapp.newsapi.model.Source;
import com.newsarcticlesapp.newsapi.model.analytics.GeneralAnalytics;
import com.newsarcticlesapp.newsapi.model.analytics.KeywordAnalytics;
import com.newsarcticlesapp.newsapi.model.analytics.SourceAnalytics;
import com.newsarcticlesapp.newsapi.model.analytics.TopicAnalytics;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsService {

    private final ArticleService articleService;
    private final ArticleKeywordService articleKeywordService;
    private final SourceService sourceService;
    private final TopicService topicService;

    public AnalyticsService(ArticleService articleService,
                            ArticleKeywordService articleKeywordService,
                            SourceService sourceService,
                            TopicService topicService) {
        this.articleService = articleService;
        this.articleKeywordService = articleKeywordService;
        this.sourceService = sourceService;
        this.topicService = topicService;
    }

    public GeneralAnalytics getGeneralAnalytics() {
        GeneralAnalytics generalAnalytics = new GeneralAnalytics();
        generalAnalytics.setAmountOfArticles(articleService.getTotalAmountOfArticles());
        generalAnalytics.setTopicsCounts(topicService.getTopTopicsWithCount());
        generalAnalytics.setKeywordCounts(articleKeywordService.getTopKeywordsCount(10));
        generalAnalytics.setSourceCounts(sourceService.getMostPopularSources(10));
        return generalAnalytics;
    }

    public TopicAnalytics getTopicAnalytics(String topicName) {
        TopicAnalytics topicAnalytics = new TopicAnalytics();
        topicAnalytics.setTopic(topicName);

        topicAnalytics.setAmountOfArticles(articleService.getAmountOfArticlesOnTopic(topicName));
        topicAnalytics.setKeywordCounts(articleKeywordService.getTopKeywordsCountForTopic(topicName, 30));
        topicAnalytics.setSourceCounts(sourceService.getMostPopularSourcesForTopic(topicName, 10));
        return topicAnalytics;
    }

    public KeywordAnalytics getKeywordAnalytics(String keyword) {
        KeywordAnalytics keywordAnalytics = new KeywordAnalytics();

        keywordAnalytics.setKeyword(keyword);
        keywordAnalytics.setAmountOfArticles(articleKeywordService.countAmountOfArticlesWithKeyword(keyword));
        keywordAnalytics.setTopicsCounts(topicService.getTopTopicsWithKeyword(keyword));
        keywordAnalytics.setSourceCounts(sourceService.getMostPopularSourcesForKeyword(keyword, 10));
        return keywordAnalytics;
    }

    public SourceAnalytics getSourceAnalytics(String sourceName) {
        SourceAnalytics sourceAnalytics = new SourceAnalytics();
        sourceAnalytics.setSource(sourceName);

        Source source = sourceService.findByName(sourceName);

        if (source == null) {
            throw new IllegalArgumentException(String.format("Cannot get analytics from unknown source: %s", sourceName));
        }

        sourceAnalytics.setAmountOfArticles(articleService.getAmountOfArticlesFromSource(source));
        sourceAnalytics.setTopicsCounts(topicService.getTopTopicsForSource(source));
        sourceAnalytics.setKeywordCounts(articleKeywordService.getTopKeywordsCountForSource(source, 10));
        return sourceAnalytics;
    }
}

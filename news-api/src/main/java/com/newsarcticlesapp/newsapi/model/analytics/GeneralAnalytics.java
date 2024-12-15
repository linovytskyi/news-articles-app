package com.newsarcticlesapp.newsapi.model.analytics;

import lombok.Data;

import java.util.List;

@Data
public class GeneralAnalytics {
    private Long amountOfArticles;
    private List<TopicsCount> topicsCounts;
    private List<KeywordCount> keywordCounts;
    private List<SourceCount> sourceCounts;
}

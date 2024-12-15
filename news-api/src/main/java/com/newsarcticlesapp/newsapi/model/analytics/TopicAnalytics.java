package com.newsarcticlesapp.newsapi.model.analytics;

import lombok.Data;

import java.util.List;

@Data
public class TopicAnalytics {
    private String topic;
    private Long amountOfArticles;
    private List<SourceCount> sourceCounts;
    private List<KeywordCount> keywordCounts;
}

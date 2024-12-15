package com.newsarcticlesapp.newsapi.model.analytics;

import lombok.Data;

import java.util.List;

@Data
public class KeywordAnalytics {
    private String keyword;
    private Long amountOfArticles;
    private List<TopicsCount> topicsCounts;
    private List<SourceCount> sourceCounts;
}

package com.newsarcticlesapp.newsapi.model.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class NewsFeedRequest {
    private Integer pageNumber;
    private Integer pageSize;
    private String topic;
    private Set<String> keywords;

    public boolean hasTopicAndKeywords() {
        return topic != null && keywords != null && !keywords.isEmpty();
    }
}

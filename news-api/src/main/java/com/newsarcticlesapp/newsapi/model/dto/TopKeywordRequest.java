package com.newsarcticlesapp.newsapi.model.dto;

import lombok.Data;

@Data
public class TopKeywordRequest {
    private String topic;
    private Integer amount;
}

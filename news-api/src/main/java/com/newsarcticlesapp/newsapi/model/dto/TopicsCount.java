package com.newsarcticlesapp.newsapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicsCount {
    private String topic;
    private Long count;
}

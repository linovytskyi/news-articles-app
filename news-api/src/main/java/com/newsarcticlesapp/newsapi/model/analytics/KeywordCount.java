package com.newsarcticlesapp.newsapi.model.analytics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeywordCount {
    private String keyword;
    private Long count;
}

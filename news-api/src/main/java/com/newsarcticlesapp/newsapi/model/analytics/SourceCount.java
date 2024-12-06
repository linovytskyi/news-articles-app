package com.newsarcticlesapp.newsapi.model.analytics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SourceCount {
    private String source;
    private Long count;
}

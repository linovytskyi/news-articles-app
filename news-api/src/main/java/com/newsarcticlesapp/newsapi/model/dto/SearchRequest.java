package com.newsarcticlesapp.newsapi.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class SearchRequest {
    private String text;
    private String topic;
    private Set<String> keywords;
    private String source;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime searchDate;
    private Integer pageNumber;
    private Integer pageSize;
}

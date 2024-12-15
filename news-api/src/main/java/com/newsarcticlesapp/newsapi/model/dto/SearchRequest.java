package com.newsarcticlesapp.newsapi.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
public class SearchRequest {
    private String text;
    private String topic;
    private List<String> keywords;
    private String source;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate searchDate;
    private Integer pageNumber;
    private Integer pageSize;
}

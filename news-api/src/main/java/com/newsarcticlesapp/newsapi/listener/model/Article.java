package com.newsarcticlesapp.newsapi.listener.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Article {
    @JsonProperty("title")
    private String title;

    @JsonProperty("article_type")
    private String articleType;

    @JsonProperty("text")
    private String text;

    @JsonProperty("keywords")
    private List<String> keywords;

    @JsonProperty("datetime")
    private LocalDateTime datetime;

    @JsonProperty("source")
    private String source;

    @JsonProperty("url")
    private String url;

    @JsonProperty("number_of_views")
    private int numberOfViews;

    @JsonProperty("picture_link")
    private String pictureLink;

    // Getters and setters
}

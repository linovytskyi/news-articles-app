package com.newsarcticlesapp.newsapi.listener.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AggregatedArticle {

    @JsonProperty("text")
    private String text;

    @JsonProperty("title")
    private String title;

    @JsonProperty("datetime")
    private LocalDateTime datetime;

    @JsonProperty("source")
    private String sourceUrl;

    @JsonProperty("url")
    private String url;

    @JsonProperty("picture_link")
    private String pictureLink;
}

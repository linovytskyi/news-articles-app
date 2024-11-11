package com.newsarcticlesapp.newsapi.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Topic {
    Integer id;
    String name;
    @JsonProperty("created_date")
    LocalDateTime createdDate;
}

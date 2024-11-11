package com.newsarcticlesapp.newsapi.client.model;

import lombok.Data;

import java.util.List;

@Data
public class ClassificationResponse {
    private Topic topic;
    private List<String> persons;
    private List<String> locations;
    private List<String> organizations;
    private List<String> keywords;
}

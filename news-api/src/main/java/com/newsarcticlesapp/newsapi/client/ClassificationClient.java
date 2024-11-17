package com.newsarcticlesapp.newsapi.client;

import com.newsarcticlesapp.newsapi.client.model.ClassificationResponse;
import com.newsarcticlesapp.newsapi.client.model.ArticleText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ClassificationClient {

    private final RestClient restClient;
    private final String baseUrl;

    private final static Logger LOGGER = LoggerFactory.getLogger(ClassificationClient.class);

    public ClassificationClient(RestClient restClient,
                                @Value("${classification-api.url}") String classificationApiUrl) {
        this.restClient = restClient;
        this.baseUrl = classificationApiUrl;
    }

    public ClassificationResponse getClassification(ArticleText request) {
        LOGGER.info("Getting classification of article text {}", request);
        ResponseEntity<ClassificationResponse> response = restClient.post()
                .uri(baseUrl + "/analyze")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toEntity(ClassificationResponse.class);

        LOGGER.info("Classification response {}", response.getBody());
        return response.getBody();
    }

}

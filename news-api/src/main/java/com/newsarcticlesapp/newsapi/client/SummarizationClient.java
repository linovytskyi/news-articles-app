package com.newsarcticlesapp.newsapi.client;

import com.newsarcticlesapp.newsapi.client.model.SummarizationResponse;
import com.newsarcticlesapp.newsapi.client.model.ArticleText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class SummarizationClient {

    private final RestClient restClient;
    private final String baseUrl;

    private final static Logger LOGGER = LoggerFactory.getLogger(SummarizationClient.class);

    public SummarizationClient(final RestClient restClient,
                               @Value("${summarization-api.url}") final String baseUrl) {
        this.restClient = restClient;
        this.baseUrl = baseUrl;
    }

    public SummarizationResponse getSummarization(ArticleText textRequest) {
        LOGGER.info("Getting summarization of article text {}", textRequest);
        ResponseEntity<SummarizationResponse> response = this.restClient.post()
                .uri(baseUrl + "/summarize")
                .contentType(MediaType.APPLICATION_JSON)
                .body(textRequest)
                .retrieve()
                .toEntity(SummarizationResponse.class);
        LOGGER.info("Summarization response {}", response);
        return response.getBody();
    }
}

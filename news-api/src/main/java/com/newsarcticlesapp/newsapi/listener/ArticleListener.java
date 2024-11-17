package com.newsarcticlesapp.newsapi.listener;

import com.newsarcticlesapp.newsapi.listener.model.AggregatedArticle;
import com.newsarcticlesapp.newsapi.service.AggregationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ArticleListener {

    private final AggregationService aggregationService;

    public ArticleListener(AggregationService aggregationService) {
        this.aggregationService = aggregationService;
    }

    @RabbitListener(queues = "news_queue")
    public void receiveMessage(String message) {
        try {
            aggregationService.processAggregatedArticleFromQueue(message);
        } catch (Exception e) {
            System.err.println("Failed to process message: " + e.getMessage());
        }
    }
}

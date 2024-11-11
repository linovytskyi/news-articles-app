package com.newsarcticlesapp.newsapi.listener;

import com.newsarcticlesapp.newsapi.listener.model.Article;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ArticleListener {

    private final ObjectMapper objectMapper;

    public ArticleListener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "news_queue")
    public void receiveMessage(String message) {
        try {
            // Deserialize the JSON message into an Article object
            Article article = objectMapper.readValue(message, Article.class);
            System.out.println("Received Article: " + article);
            // Process the article (e.g., save to a database, log, etc.)
        } catch (Exception e) {
            System.err.println("Failed to process message: " + e.getMessage());
        }
    }
}

package com.newsarcticlesapp.newsapi.controller;

import com.newsarcticlesapp.newsapi.model.dto.TopKeywordRequest;
import com.newsarcticlesapp.newsapi.service.ArticleKeywordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/keyword")
@CrossOrigin
public class KeywordController {

    private final ArticleKeywordService articleKeywordService;
    private static final Logger LOGGER = LoggerFactory.getLogger(KeywordController.class);

    public KeywordController(ArticleKeywordService articleKeywordService) {
        this.articleKeywordService = articleKeywordService;
    }

    @PostMapping("/top")
    public ResponseEntity<List<String>> getMostPopularKeywords(@RequestBody TopKeywordRequest request) {
        LOGGER.info("Got request to get most popular keywords with request: {}", request);
        String topic = request.getTopic();
        Integer amount = request.getAmount();
        if (topic != null && !topic.isEmpty()) {
            return ResponseEntity.ok(articleKeywordService.getTopKeywordsForTopic(topic, amount));
        } else {
            return ResponseEntity.ok(articleKeywordService.getTopKeywords(amount));
        }
    }
}

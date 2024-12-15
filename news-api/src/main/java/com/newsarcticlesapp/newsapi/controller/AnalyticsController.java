package com.newsarcticlesapp.newsapi.controller;

import com.newsarcticlesapp.newsapi.model.analytics.GeneralAnalytics;
import com.newsarcticlesapp.newsapi.model.analytics.KeywordAnalytics;
import com.newsarcticlesapp.newsapi.model.analytics.SourceAnalytics;
import com.newsarcticlesapp.newsapi.model.analytics.TopicAnalytics;
import com.newsarcticlesapp.newsapi.service.AnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/analytics")
@CrossOrigin
public class AnalyticsController {

    private final AnalyticsService analyticsService;
    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping
    public ResponseEntity<GeneralAnalytics> getGeneralAnalytics() {
        return ResponseEntity.ok(analyticsService.getGeneralAnalytics());
    }

    @GetMapping("/topic")
    public ResponseEntity<TopicAnalytics> getTopicAnalytics(@RequestParam String topic) {
        return ResponseEntity.ok(analyticsService.getTopicAnalytics(topic));
    }

    @GetMapping("/keyword")
    public ResponseEntity<KeywordAnalytics> getKeywordAnalytics(@RequestParam String keyword) {
        return ResponseEntity.ok(analyticsService.getKeywordAnalytics(keyword));
    }

    @GetMapping("/source")
    public ResponseEntity<SourceAnalytics> getSourceAnalytics(@RequestParam String source) {
        return ResponseEntity.ok(analyticsService.getSourceAnalytics(source));
    }
}

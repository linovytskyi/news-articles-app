package com.newsarcticlesapp.newsapi.controller;

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


    @GetMapping("/topic")
    public ResponseEntity<TopicAnalytics> getTopicAnalytics(@RequestParam String topic) {
        return ResponseEntity.ok(analyticsService.getTopicAnalytics(topic));
    }
}

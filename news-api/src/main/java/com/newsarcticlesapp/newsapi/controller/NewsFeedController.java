package com.newsarcticlesapp.newsapi.controller;

import com.newsarcticlesapp.newsapi.model.NewsFeed;
import com.newsarcticlesapp.newsapi.model.dto.NewsFeedRequest;
import com.newsarcticlesapp.newsapi.service.NewsFeedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news-feed")
@CrossOrigin
public class NewsFeedController {

    private final NewsFeedService newsFeedService;

    public NewsFeedController(final NewsFeedService newsFeedService) {
        this.newsFeedService = newsFeedService;
    }

    @PostMapping
    public ResponseEntity<NewsFeed> getNewsFeed(@RequestBody NewsFeedRequest newsFeedRequest) {
        return ResponseEntity.ok(newsFeedService.getNewsFeed(newsFeedRequest));
    }
}

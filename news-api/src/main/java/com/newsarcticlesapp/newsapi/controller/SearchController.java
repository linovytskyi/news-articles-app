package com.newsarcticlesapp.newsapi.controller;

import com.newsarcticlesapp.newsapi.model.SearchArticle;
import com.newsarcticlesapp.newsapi.model.dto.SearchRequest;
import com.newsarcticlesapp.newsapi.model.dto.SearchResponse;
import com.newsarcticlesapp.newsapi.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
@CrossOrigin
public class SearchController {

    private final SearchService searchService;
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }


    @PostMapping
    public ResponseEntity<SearchResponse> searchArticle(@RequestBody SearchRequest searchRequest) {
        LOGGER.info("Search request: {}", searchRequest);
        return ResponseEntity.ok(searchService.searchArticles(searchRequest));
    }

}

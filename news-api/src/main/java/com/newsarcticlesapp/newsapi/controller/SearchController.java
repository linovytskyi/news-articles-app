package com.newsarcticlesapp.newsapi.controller;

import com.newsarcticlesapp.newsapi.model.SearchArticle;
import com.newsarcticlesapp.newsapi.model.dto.SearchRequest;
import com.newsarcticlesapp.newsapi.model.dto.SearchResponse;
import com.newsarcticlesapp.newsapi.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
@CrossOrigin
public class SearchController {

    private final SearchService searchService;
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }


    @PostMapping
    public ResponseEntity<SearchResponse> searchArticle(@RequestBody SearchRequest searchRequest) {
        return ResponseEntity.ok(searchService.searchArticles(searchRequest));
    }

}

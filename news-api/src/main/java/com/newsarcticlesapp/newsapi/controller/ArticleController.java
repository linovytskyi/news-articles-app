package com.newsarcticlesapp.newsapi.controller;

import com.newsarcticlesapp.newsapi.model.Article;
import com.newsarcticlesapp.newsapi.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public ResponseEntity<Article> saveArticle(@RequestBody Article article) {
        Article saveArticle = articleService.saveArticle(article);
        return ResponseEntity.ok(saveArticle);
    }

    @GetMapping
    public ResponseEntity<List<Article>> getArticles() {
        return ResponseEntity.ok(articleService.findAll());
    }
}

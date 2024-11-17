package com.newsarcticlesapp.newsapi.controller;

import com.newsarcticlesapp.newsapi.client.ClassificationClient;
import com.newsarcticlesapp.newsapi.client.model.ArticleText;
import com.newsarcticlesapp.newsapi.model.Article;
import com.newsarcticlesapp.newsapi.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;
    private final ClassificationClient classificationClient;

    public ArticleController(ArticleService articleService,
                             ClassificationClient classificationClient) {
        this.articleService = articleService;
        this.classificationClient = classificationClient;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(articleService.findArticleById(id));
    }

    @GetMapping
    public ResponseEntity<List<Article>> getAll() {
        return ResponseEntity.ok(articleService.findAllArticles());
    }
}

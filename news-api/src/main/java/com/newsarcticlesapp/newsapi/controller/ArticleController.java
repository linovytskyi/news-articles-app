package com.newsarcticlesapp.newsapi.controller;

import com.newsarcticlesapp.newsapi.client.ClassificationClient;
import com.newsarcticlesapp.newsapi.client.SummarizationClient;
import com.newsarcticlesapp.newsapi.client.model.TextRequest;
import com.newsarcticlesapp.newsapi.model.Article;
import com.newsarcticlesapp.newsapi.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;
    private final ClassificationClient classificationClient;

    public ArticleController(ArticleService articleService,
                             ClassificationClient classificationClient) {
        this.articleService = articleService;
        this.classificationClient = classificationClient;
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

    @PostMapping("/test")
    public ResponseEntity<Void> test(@RequestBody TextRequest text) {
        classificationClient.classifyText(text.getText());

        return ResponseEntity.ok().build();
    }
}

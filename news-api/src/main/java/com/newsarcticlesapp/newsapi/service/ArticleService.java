package com.newsarcticlesapp.newsapi.service;

import com.newsarcticlesapp.newsapi.model.Article;
import com.newsarcticlesapp.newsapi.repository.ArticleRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(ArticleService.class);

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> findAll() {
        LOGGER.info("Getting all articles");
        return articleRepository.findAll();
    }

    public Article saveArticle(Article article) {
        LOGGER.info("Saving article {}", article);
        return articleRepository.save(article);
    }

}

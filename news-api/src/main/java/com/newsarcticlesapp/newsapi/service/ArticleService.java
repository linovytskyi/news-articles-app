package com.newsarcticlesapp.newsapi.service;

import com.newsarcticlesapp.newsapi.model.*;
import com.newsarcticlesapp.newsapi.model.analytics.SourceCount;
import com.newsarcticlesapp.newsapi.repository.ArticleKeywordRepository;
import com.newsarcticlesapp.newsapi.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleKeywordRepository articleKeywordRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository,
                          ArticleKeywordRepository articleKeywordRepository) {
        this.articleRepository = articleRepository;
        this.articleKeywordRepository = articleKeywordRepository;
    }

    public List<String> getAllArticleTopics() {
        return articleRepository.findAllTopics();
    }

    public Article findArticleById(Long id) {
        Optional<Article> article = articleRepository.findById(id);

        if (article.isEmpty()) {
            throw new NoSuchElementException("No such article");
        }
        Article foundArticle = article.get();
        Set<ArticleKeyword> articleKeywords = articleKeywordRepository.findAllByArticleId(id);
        foundArticle.setKeywords(articleKeywords);

        return foundArticle;
    }

    public List<Article> findAllArticles() {
        List<Article> articles = articleRepository.findAll();
        for (Article article : articles) {
            Set<ArticleKeyword> articleKeywords = articleKeywordRepository.findAllByArticleId(article.getId());
            article.setKeywords(articleKeywords);
        }
        return articles;
    }

    public Optional<Article> findArticleByOriginalTitleAndSource(String originalTitle, Source source) {
        return articleRepository.findByOriginalTitleAndSource(originalTitle, source);
    }

    @Transactional
    public Article saveArticle(Article article) {
        Article savedArticle = articleRepository.save(article);
        article.getKeywords().forEach(keyword -> keyword.setArticleId(savedArticle.getId()));
        article.getKeywords().forEach(keyword -> keyword.setArticleTopic(savedArticle.getTopic()));
        articleKeywordRepository.saveAll(article.getKeywords());
        return savedArticle;
    }

    public long getTotalAmountOfArticles() {
        return articleRepository.count();
    }

    public long getAmountOfArticlesOnTopic(String topic) {
        return articleRepository.countArticlesByTopic(topic);
    }

    public long getAmountOfArticlesFromSource(Source source) {
        return articleRepository.countArticlesBySource(source);
    }
}

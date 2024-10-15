package com.newsarcticlesapp.newsapi.repository;

import com.newsarcticlesapp.newsapi.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


@Component
public interface ArticleRepository extends JpaRepository<Article, Long> {

}

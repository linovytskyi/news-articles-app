package com.newsarcticlesapp.newsapi.repository;

import com.newsarcticlesapp.newsapi.model.Article;
import com.newsarcticlesapp.newsapi.model.Source;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Component
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Optional<Article> findByOriginalTitleAndSource(String originalTitle, Source source);

    @Query("SELECT a.topic AS topic, COUNT(a.topic) AS topicCount " +
            "FROM Article a " +
            "GROUP BY a.topic " +
            "ORDER BY topicCount DESC")
    List<Map<String, Object>> findTopTopics(
            Pageable pageable
    );
}

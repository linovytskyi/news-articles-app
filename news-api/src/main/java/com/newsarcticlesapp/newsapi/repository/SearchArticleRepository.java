package com.newsarcticlesapp.newsapi.repository;

import com.newsarcticlesapp.newsapi.model.SearchArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Set;

@Repository
public interface SearchArticleRepository extends JpaRepository<SearchArticle, Long> {


    @Query("SELECT sa " +
            "FROM SearchArticle sa " +
            "JOIN sa.source src " +
            "WHERE (:text IS NULL OR LOWER(sa.title) LIKE LOWER(CONCAT('%', :text, '%'))) " +
            "AND (:topic IS NULL OR sa.topic = :topic) " +
            "AND (:source IS NULL OR src.name = :source) " +
            "ORDER BY sa.postedAt DESC")
    Page<SearchArticle> searchArticlesByTopicTextAndSource(
            @Param("text") String text,
            @Param("topic") String topic,
            @Param("source") String source,
            Pageable pageable);
}

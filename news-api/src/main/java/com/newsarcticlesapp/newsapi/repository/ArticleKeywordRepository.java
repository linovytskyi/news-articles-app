package com.newsarcticlesapp.newsapi.repository;

import com.newsarcticlesapp.newsapi.model.ArticleKeyword;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public interface ArticleKeywordRepository extends JpaRepository<ArticleKeyword, Long> {

    Set<ArticleKeyword> findAllByArticleId(Long articleId);

    @Query("SELECT ak.value AS keyword, COUNT(ak.value) AS keywordCount " +
            "FROM ArticleKeyword ak " +
            "GROUP BY ak.value " +
            "ORDER BY keywordCount DESC")
    List<Map<String, Object>> findTopKeywords(
            Pageable pageable
    );

    @Query("SELECT ak.value AS keyword, COUNT(ak.value) AS keywordCount " +
            "FROM ArticleKeyword ak " +
            "JOIN Article a ON ak.articleId = a.id " +
            "WHERE a.topic = :topic " +
            "GROUP BY ak.value " +
            "ORDER BY COUNT(ak.value) DESC")
    List<Map<String, Object>> findTopKeywordsForTopic(
            @Param("topic") String topic,
            Pageable pageable
    );
}

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
            "WHERE ak.articleTopic = :topic " +
            "GROUP BY ak.value " +
            "ORDER BY COUNT(ak.value) DESC")
    List<Map<String, Object>> findTopKeywordsForTopic(
            @Param("topic") String topic,
            Pageable pageable
    );


    @Query("SELECT COUNT(DISTINCT ak.articleId) " +
            "FROM ArticleKeyword ak " +
            "WHERE ak.value = :value")
    long countArticlesWithKeywordValue(@Param("value") String value);

    @Query("SELECT ak.articleTopic AS topic, COUNT(ak.articleTopic) AS topicCount " +
            "FROM ArticleKeyword ak " +
            "WHERE ak.value = :value " +
            "GROUP BY ak.articleTopic " +
            "ORDER BY COUNT(ak.articleTopic) DESC")
    List<Map<String, Object>> findTopTopicsForKeywordValue(
            @Param("value") String value,
            Pageable pageable
    );

    @Query("SELECT a.source.name AS sourceName, COUNT(a.source.name) AS articleCount " +
            "FROM ArticleKeyword ak " +
            "JOIN Article a ON ak.articleId = a.id " +
            "WHERE ak.value = :value " +
            "GROUP BY a.source.name " +
            "ORDER BY COUNT(a.source.name) DESC")
    List<Map<String, Object>> findTopSourcesForKeywordValue(
            @Param("value") String value,
            Pageable pageable);


    @Query("SELECT ak.value AS keyword, COUNT(ak.value) AS keywordCount " +
            "FROM ArticleKeyword ak " +
            "JOIN Article a ON ak.articleId = a.id " +
            "WHERE a.source.id = :sourceId " +
            "GROUP BY ak.value " +
            "ORDER BY COUNT(ak.value) DESC")
    List<Map<String, Object>> findTopKeywordsForSource(
            @Param("sourceId") Long sourceId,
            Pageable pageable
    );

    @Query("SELECT COUNT(ak) " +
            "FROM ArticleKeyword ak " +
            "WHERE ak.value = :value")
    long countArticleKeywordsByValue(String value);
}

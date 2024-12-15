package com.newsarcticlesapp.newsapi.repository;

import com.newsarcticlesapp.newsapi.model.Article;
import com.newsarcticlesapp.newsapi.model.Source;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("SELECT a.source.name AS sourceName, COUNT(a.source.name) AS articleCount " +
            "FROM Article a " +
            "GROUP BY a.source.name " +
            "ORDER BY articleCount DESC")
    List<Map<String, Object>> findTopSources(
            Pageable pageable
    );

    @Query("SELECT a.source.name AS sourceName, COUNT(a.source.name) AS articleCount " +
            "FROM Article a " +
            "WHERE a.topic = :topic " +
            "GROUP BY a.source.name " +
            "ORDER BY articleCount DESC")
    List<Map<String, Object>> findTopSourcesForTopic(
            @Param("topic") String topic,
            Pageable pageable
    );

    @Query("SELECT a.topic AS topic, COUNT(a.topic) AS topicCount " +
            "FROM Article a " +
            "WHERE a.source.id = :sourceId " +
            "GROUP BY a.topic " +
            "ORDER BY topicCount DESC")
    List<Map<String, Object>> findTopTopicsForSource(
            @Param("sourceId") Long sourceId,
            Pageable pageable
    );



    @Query("SELECT DISTINCT a.topic FROM Article a")
    List<String> findAllTopics();

    @Query("SELECT COUNT(a) " +
            "FROM Article a " +
            "WHERE a.topic = :topic")
    long countArticlesByTopic(@Param("topic") String topic);

    @Query("SELECT COUNT(a) " +
            "FROM Article a " +
            "WHERE a.source = :source")
    long countArticlesBySource(@Param("source") Source source);
}

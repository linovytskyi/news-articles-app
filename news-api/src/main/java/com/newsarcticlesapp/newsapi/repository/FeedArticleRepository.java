package com.newsarcticlesapp.newsapi.repository;

import com.newsarcticlesapp.newsapi.model.FeedArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface FeedArticleRepository extends JpaRepository<FeedArticle, Long> {
    @Query("SELECT DISTINCT fa " +
            "FROM FeedArticle fa " +
            "WHERE (:topic IS NULL OR fa.topic = :topic) " +
            "AND (:keywords IS NULL OR " +
            "    (SELECT COUNT(DISTINCT ak1.value) " +
            "     FROM ArticleKeyword ak1 " +
            "     WHERE ak1.articleId = fa.id AND ak1.value IN :keywords) = :keywordCount) " +
            "ORDER BY fa.postedAt DESC")
    Page<FeedArticle> findAllByTopicAndWithKeywords(
            @Param("topic") String topic,
            @Param("keywords") Set<String> keywords,
            @Param("keywordCount") Long keywordCount,
            Pageable pageable);
}

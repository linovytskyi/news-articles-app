package com.newsarcticlesapp.newsapi.repository;

import com.newsarcticlesapp.newsapi.model.SearchArticle;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class SearchArticleRepository {

    private EntityManager entityManager;
    public SearchArticleRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Page<SearchArticle> searchArticles(String text, String topic, List<String> keywords, String source, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        StringBuilder sql = new StringBuilder(
                "SELECT sa " +
                        "FROM SearchArticle sa " +
                        "JOIN sa.source src " +
                        "JOIN ArticleKeyword ak ON ak.articleId = sa.id " +
                        "WHERE 1=1 "
        );

        if (text != null && !text.trim().isEmpty()) {
            sql.append("AND LOWER(sa.title) LIKE LOWER(CONCAT('%', :text, '%')) ");
        }
        if (topic != null && !topic.trim().isEmpty()) {
            sql.append("AND sa.topic = :topic ");
        }
        if (source != null && !source.trim().isEmpty()) {
            sql.append("AND src.name = :source ");
        }
        if (startDate != null) {
            sql.append("AND sa.postedAt >= :startDate ");
        }
        if (endDate != null) {
            sql.append("AND sa.postedAt <= :endDate ");
        }
        if (keywords != null && !keywords.isEmpty()) {
            sql.append("AND ak.value IN :keywords ");
            sql.append("GROUP BY sa.id ");
            sql.append("HAVING COUNT(DISTINCT ak.value) = :keywordCount ");
        }
        sql.append("ORDER BY sa.postedAt DESC");

        Query query = entityManager.createQuery(sql.toString(), SearchArticle.class);

        if (text != null && !text.trim().isEmpty()) {
            query.setParameter("text", text);
        }
        if (topic != null && !topic.trim().isEmpty()) {
            query.setParameter("topic", topic);
        }
        if (source != null && !source.trim().isEmpty()) {
            query.setParameter("source", source);
        }
        if (startDate != null) {
            query.setParameter("startDate", startDate);
        }
        if (endDate != null) {
            query.setParameter("endDate", endDate);
        }
        if (keywords != null && !keywords.isEmpty()) {
            query.setParameter("keywords", keywords);
            query.setParameter("keywordCount", keywords.size());
        }


        List<SearchArticle> results = query.getResultList();
        long total = results.size();

        return new PageImpl<>(results, pageable, total);
    }

    public List<SearchArticle> findArticlesByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of(); // Return an empty list if no IDs are provided
        }

        String sql = "SELECT sa FROM SearchArticle sa WHERE sa.id IN :ids";
        Query query = entityManager.createQuery(sql, SearchArticle.class);
        query.setParameter("ids", ids);

        return query.getResultList();
    }
}

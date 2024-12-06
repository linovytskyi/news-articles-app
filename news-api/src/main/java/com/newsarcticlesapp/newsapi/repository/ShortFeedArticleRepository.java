package com.newsarcticlesapp.newsapi.repository;

import com.newsarcticlesapp.newsapi.model.ShortFeedArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShortFeedArticleRepository extends JpaRepository<ShortFeedArticle, Long> {
    Page<ShortFeedArticle> findAllByOrderByPostedAtDesc(Pageable pageable);

    List<ShortFeedArticle> findByTopicAndPostedAtBeforeOrderByPostedAtDesc(String topic, LocalDateTime startDate, Pageable pageable);
}

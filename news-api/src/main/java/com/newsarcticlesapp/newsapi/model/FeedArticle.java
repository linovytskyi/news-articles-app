package com.newsarcticlesapp.newsapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "articles")
public class FeedArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String topic;
    @Column(name = "picture_url")
    private String pictureUrl;
    @Column(name = "posted_at")
    private LocalDateTime postedAt;
    @ManyToOne
    @JoinColumn(name = "source_id", nullable = false)
    private Source source;
    @Lob
    private String summary;
    @Transient
    private List<ShortFeedArticle> topicArticles;
    @Transient
    private List<String> keywords;
}

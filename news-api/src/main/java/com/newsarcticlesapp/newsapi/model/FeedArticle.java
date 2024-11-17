package com.newsarcticlesapp.newsapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "articles")
public class FeedArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String topic;
    @Column(name = "picture_link")
    private String pictureLink;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
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

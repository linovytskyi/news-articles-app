package com.newsarcticlesapp.newsapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "articles")
public class ShortFeedArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String title;
    private String topic;
    @Column(name = "picture_url")
    private String pictureUrl;
    @Column(name = "posted_at")
    private LocalDateTime postedAt;
    @ManyToOne
    @JoinColumn(name = "source_id", nullable = false)
    private Source source;
}

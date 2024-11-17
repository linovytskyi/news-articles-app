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
    @Column(name = "picture_link")
    private String pictureLink;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "source_id", nullable = false)
    private Source source;
}

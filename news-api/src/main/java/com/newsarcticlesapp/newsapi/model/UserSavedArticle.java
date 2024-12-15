package com.newsarcticlesapp.newsapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users_saved_articles")
public class UserSavedArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer userId;
    private Integer articleId;
    @Column(name = "saved_at")
    private LocalDateTime savedAt;
}

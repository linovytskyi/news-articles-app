package com.newsarcticlesapp.newsapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "article_keywords")
public class ArticleKeyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "article_id", nullable = false)
    private Long articleId;
    private String value;
    @Enumerated(EnumType.STRING)
    private KeywordType type;
    @Column(name = "article_topic", nullable = false)
    private String articleTopic;

    public ArticleKeyword(Long articleId, String value, KeywordType type, String articleTopic) {
        this.articleId = articleId;
        this.value = value;
        this.type = type;
        this.articleTopic = articleTopic;
    }

    public ArticleKeyword(String value, KeywordType type) {
        this.value = value;
        this.type = type;
    }
}

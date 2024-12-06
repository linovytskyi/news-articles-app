package com.newsarcticlesapp.newsapi.model;

import com.newsarcticlesapp.newsapi.listener.model.AggregatedArticle;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalTitle;

    private String title;

    @Column(name = "topic")
    private String topic;

    @Lob
    private String text;

    @Lob
    private String summary;

    @Column(name = "posted_at")
    private LocalDateTime postedAt;

    @ManyToOne
    @JoinColumn(name = "source_id", nullable = false)
    private Source source;

    @Column(name = "original_url")
    private String originalUrl;

    @Column(name = "picture_url")
    private String pictureUrl;

    @Transient
    private Set<ArticleKeyword> keywords = new HashSet<>();

    public static Article createBaseArticleFromAggregated(AggregatedArticle aggregatedArticle) {
        Article article = new Article();
        article.setOriginalTitle(aggregatedArticle.getTitle());
        article.setTitle(aggregatedArticle.getTitle());
        article.setText(aggregatedArticle.getText());
        article.setPostedAt(aggregatedArticle.getDatetime());
        article.setOriginalUrl(aggregatedArticle.getUrl());
        article.setPictureUrl(aggregatedArticle.getPictureLink());
        return article;
    }
}

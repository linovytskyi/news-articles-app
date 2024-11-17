package com.newsarcticlesapp.newsapi.model;

import com.newsarcticlesapp.newsapi.listener.model.AggregatedArticle;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "source_id", nullable = false)
    private Source source;

    private String url;

    @Column(name = "picture_link")
    private String pictureLink;

    @Transient
    private Set<ArticleKeyword> keywords = new HashSet<>();

    public static Article createBaseArticleFromAggregated(AggregatedArticle aggregatedArticle) {
        Article article = new Article();
        article.setOriginalTitle(aggregatedArticle.getTitle());
        article.setText(aggregatedArticle.getText());
        article.setCreatedAt(aggregatedArticle.getDatetime());
        article.setUrl(aggregatedArticle.getUrl());
        article.setPictureLink(aggregatedArticle.getPictureLink());
        return article;
    }
}

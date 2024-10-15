package com.newsarcticlesapp.newsapi.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "text", nullable = false, columnDefinition = "TEXT")
    private String text;

    @Column(name = "author")
    private String author;

    @Column(name = "source")
    private String source;

    @Column(name = "url")
    private String url;

    @Column(name = "type", length = 100)
    private String type;
}

package com.newsarcticlesapp.newsapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "sources")
public class Source {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String url;
}


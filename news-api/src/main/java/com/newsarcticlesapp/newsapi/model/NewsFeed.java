package com.newsarcticlesapp.newsapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsFeed {
    private List<FeedArticle> feedArticles;
    private Integer pageNumber;
    private Integer pageSize;
}

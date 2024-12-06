package com.newsarcticlesapp.newsapi.model.dto;

import com.newsarcticlesapp.newsapi.model.SearchArticle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResponse {
    private List<SearchArticle> searchArticleList;
    private Integer pageNumber;
    private Integer pageSize;
}

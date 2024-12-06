package com.newsarcticlesapp.newsapi.service;

import com.newsarcticlesapp.newsapi.model.SearchArticle;
import com.newsarcticlesapp.newsapi.model.dto.SearchRequest;
import com.newsarcticlesapp.newsapi.model.dto.SearchResponse;
import com.newsarcticlesapp.newsapi.repository.SearchArticleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SearchService {

    private final SearchArticleRepository searchArticleRepository;

    public SearchService(SearchArticleRepository searchArticleRepository) {
        this.searchArticleRepository = searchArticleRepository;
    }

    public SearchResponse searchArticles(SearchRequest request) {
        Set<String> keywords = request.getKeywords();
        if (keywords == null || keywords.isEmpty()) {
            keywords = null;
        }

        String source = request.getSource();
        if (source == null || source.isEmpty()) {
            source = null;
        }

        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize(), Sort.by(Sort.Direction.DESC, "postedAt"));

        System.out.println(request);
        System.out.println(source);

        Page<SearchArticle> searchArticlePage = this.searchArticleRepository.searchArticlesByTopicTextAndSource(
                request.getText(),
                request.getTopic(),
                source,
                pageable
        );

        return new SearchResponse(searchArticlePage.getContent(), searchArticlePage.getNumber(), searchArticlePage.getSize());
    }
}

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

import java.time.LocalDateTime;
import java.time.LocalTime;
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

        String text = request.getText();

        if (request.getText() == null || request.getText().trim().isEmpty()) {
            text = null;
        }

        List<String> keywords = request.getKeywords();
        if (keywords == null || keywords.isEmpty()) {
            keywords = null;
        }

        String source = request.getSource();
        if (source == null || source.isEmpty()) {
            source = null;
        }

        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "postedAt"));

        LocalDateTime startDate = null;
        LocalDateTime endDate = null;

        if (request.getSearchDate() != null) {
            startDate = request.getSearchDate().atStartOfDay();
            endDate = request.getSearchDate().atTime(LocalTime.MAX);
        }

        if (request.getStartDate() != null) {
            startDate = request.getStartDate().atTime(LocalTime.MIN);
        }

        if (request.getEndDate() != null) {
            endDate = request.getEndDate().atTime(LocalTime.MAX);
        }

        Page<SearchArticle> searchArticlePage = this.searchArticleRepository.searchArticles(
                text,
                request.getTopic(),
                keywords,
                source,
                startDate,
                endDate,
                pageable
        );

        System.out.println("total: " + searchArticlePage.getTotalPages());

        return new SearchResponse(searchArticlePage.getContent(), searchArticlePage.getNumber(), searchArticlePage.getSize());
    }
}

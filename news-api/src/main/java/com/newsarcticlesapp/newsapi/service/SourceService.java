package com.newsarcticlesapp.newsapi.service;

import com.newsarcticlesapp.newsapi.model.Source;
import com.newsarcticlesapp.newsapi.model.analytics.SourceCount;
import com.newsarcticlesapp.newsapi.repository.ArticleKeywordRepository;
import com.newsarcticlesapp.newsapi.repository.ArticleRepository;
import com.newsarcticlesapp.newsapi.repository.SourceRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SourceService {

    private final SourceRepository sourceRepository;
    private final ArticleRepository articleRepository;
    private final ArticleKeywordRepository articleKeywordRepository;
    private static final String SOURCE_NAME_KEY = "sourceName";
    private static final String ARTICLE_COUNT = "articleCount";

    public SourceService(SourceRepository sourceRepository,
                         ArticleRepository articleRepository,
                         ArticleKeywordRepository articleKeywordRepository) {
        this.sourceRepository = sourceRepository;
        this.articleRepository = articleRepository;
        this.articleKeywordRepository = articleKeywordRepository;
    }

    public Source findByName(String name) {
        return sourceRepository.findByName(name);
    }

    public List<Source> findAllSources() {
        return sourceRepository.findAll();
    }

    public List<SourceCount> getMostPopularSources(Integer amount) {
        List<Map<String, Object>> listOfSourceCountMap = articleRepository.findTopSources(PageRequest.of(0, amount));
        return mapListOfSourcesCountMapToSourcesCountList(listOfSourceCountMap);
    }

    public List<SourceCount> getMostPopularSourcesForTopic(String topic, Integer amount) {
        List<Map<String, Object>> listOfSourceCountMap = articleRepository.findTopSourcesForTopic(topic, PageRequest.of(0, amount));
        return mapListOfSourcesCountMapToSourcesCountList(listOfSourceCountMap);
    }

    public List<SourceCount> getMostPopularSourcesForKeyword(String keyword, Integer amount) {
        List<Map<String, Object>> listOfSourceCountMap = articleKeywordRepository.findTopSourcesForKeywordValue(keyword, PageRequest.of(0, amount));
        return mapListOfSourcesCountMapToSourcesCountList(listOfSourceCountMap);
    }

    private List<SourceCount> mapListOfSourcesCountMapToSourcesCountList(List<Map<String, Object>> listOfSourceCountMap) {
        List<SourceCount> sourcesCountsMap = new ArrayList<>();

        for (Map<String, Object> sourceCountMap : listOfSourceCountMap) {
            String sourceName = (String) sourceCountMap.get(SOURCE_NAME_KEY);
            Long count = (Long) sourceCountMap.get(ARTICLE_COUNT);
            sourcesCountsMap.add(new SourceCount(sourceName, count));
        }

        return sourcesCountsMap;
    }
}

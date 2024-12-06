package com.newsarcticlesapp.newsapi.service;

import com.newsarcticlesapp.newsapi.model.FeedArticle;
import com.newsarcticlesapp.newsapi.model.ShortFeedArticle;
import com.newsarcticlesapp.newsapi.repository.ArticleKeywordRepository;
import com.newsarcticlesapp.newsapi.repository.FeedArticleRepository;
import com.newsarcticlesapp.newsapi.repository.ShortFeedArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class FeedArticleService {

    private final FeedArticleRepository feedArticleRepository;
    private final ShortFeedArticleRepository shortFeedArticleRepository;
    private final ArticleKeywordRepository articleKeywordRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedArticleService.class);
    private static final Integer DEFAULT_AMOUNT_OF_SHORT_ARTICLES = 3;

    public FeedArticleService(FeedArticleRepository feedArticleRepository,
                              ShortFeedArticleRepository shortFeedArticleRepository,
                              ArticleKeywordRepository articleKeywordRepository) {
        this.feedArticleRepository = feedArticleRepository;
        this.shortFeedArticleRepository = shortFeedArticleRepository;
        this.articleKeywordRepository = articleKeywordRepository;
    }

    public Page<FeedArticle> findMostRecentOnTopicWithKeywordsPaged(String topic, Set<String> keywords, Pageable pageRequest) {
        long keywordsSize = keywords == null ? 0 : keywords.size();
        return feedArticleRepository.findAllByTopicAndWithKeywords(topic, keywords, keywordsSize, pageRequest);
    }

    public List<ShortFeedArticle> findOlderShortFeedArticlesOnSameTopicFor(FeedArticle article) {
        return findOlderShortFeedArticlesOnSameTopicFor(article, DEFAULT_AMOUNT_OF_SHORT_ARTICLES);
    }

    public List<ShortFeedArticle> findOlderShortFeedArticlesOnSameTopicFor(FeedArticle article, Integer amount) {
        String topic = article.getTopic();
        LocalDateTime createdAtBefore = article.getPostedAt().minusMinutes(30);
        return shortFeedArticleRepository.findByTopicAndPostedAtBeforeOrderByPostedAtDesc(topic, createdAtBefore, PageRequest.of(0, amount));
    }
}

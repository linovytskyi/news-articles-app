package com.newsarcticlesapp.newsapi.service;

import com.newsarcticlesapp.newsapi.model.FeedArticle;
import com.newsarcticlesapp.newsapi.model.NewsFeed;
import com.newsarcticlesapp.newsapi.model.ShortFeedArticle;
import com.newsarcticlesapp.newsapi.model.dto.NewsFeedRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class NewsFeedService {

    private final FeedArticleService feedArticleService;
    private final ArticleKeywordService articleKeywordService;

    public NewsFeedService(FeedArticleService feedArticleService,
                           ArticleKeywordService articleKeywordService) {
        this.feedArticleService = feedArticleService;
        this.articleKeywordService = articleKeywordService;
    }

    @Transactional
    public NewsFeed getNewsFeed(NewsFeedRequest request) {
        List<FeedArticle> feedArticles = getFeedArticleForPage(request);
        return new NewsFeed(feedArticles, request.getPageNumber(), request.getPageSize());
    }


    private List<FeedArticle> getFeedArticleForPage(NewsFeedRequest request) {
        Set<String> keywords = request.getKeywords();

        if (keywords == null || keywords.isEmpty()) {
            request.setKeywords(null);
        }

        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize(), Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<FeedArticle> feedArticlePage = feedArticleService.findMostRecentOnTopicWithKeywordsPaged(request.getTopic(), request.getKeywords(), pageable);
        return mapPagedFeedArticleToFeedArticleList(feedArticlePage);
    }

    private List<FeedArticle> mapPagedFeedArticleToFeedArticleList(Page<FeedArticle> pagedFeedArticles) {
        List<FeedArticle> feedArticles = pagedFeedArticles.getContent();
        feedArticles.forEach(this::enrichFeedArticlePage);
        return feedArticles;
    }

    private void enrichFeedArticlePage(FeedArticle feedArticle) {
        setOlderShortFeedArticlesOnSameTopic(feedArticle);
        setKeywords(feedArticle);
    }

    private void setOlderShortFeedArticlesOnSameTopic(FeedArticle feedArticle) {
        List<ShortFeedArticle> shortFeedArticles = feedArticleService.findOlderShortFeedArticlesOnSameTopicFor(feedArticle);
        feedArticle.setTopicArticles(shortFeedArticles);
    }

    private void setKeywords(FeedArticle feedArticle) {
        feedArticle.setKeywords(articleKeywordService.getArticleKeywordsAsListSet(feedArticle.getId()));
    }
}

package com.newsarcticlesapp.newsapi.service;

import com.newsarcticlesapp.newsapi.model.SearchArticle;
import com.newsarcticlesapp.newsapi.model.UserSavedArticle;
import com.newsarcticlesapp.newsapi.repository.SearchArticleRepository;
import com.newsarcticlesapp.newsapi.repository.UserRepository;
import com.newsarcticlesapp.newsapi.repository.UserSavedArticlesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserSavedArticlesRepository userSavedArticlesRepository;
    private final SearchArticleRepository searchArticleRepository;
    private final UserRepository userRepository;

    public UserService(UserSavedArticlesRepository userSavedArticlesRepository,
                       SearchArticleRepository searchArticleRepository,
                       UserRepository userRepository) {
        this.userSavedArticlesRepository = userSavedArticlesRepository;
        this.searchArticleRepository = searchArticleRepository;
        this.userRepository = userRepository;
    }

    public List<SearchArticle> getUserSavedArticles(Integer userId) {
        List<UserSavedArticle> savedArticles = this.userSavedArticlesRepository.findByUserId(userId);
        List<Integer> articleIds = savedArticles.stream().map(UserSavedArticle::getArticleId).toList();
        return searchArticleRepository.findArticlesByIds(articleIds);
    }

    public void saveArticle(Integer userId, Integer articleId) {
        UserSavedArticle savedArticle;

        savedArticle = userSavedArticlesRepository.findByUserIdAndArticleId(userId, articleId);

        if (savedArticle != null) {
            throw new IllegalArgumentException(String.format("Article with id %s was already saved by user with id %s", articleId, userId));
        }

        savedArticle = new UserSavedArticle();

        savedArticle.setArticleId(articleId);
        savedArticle.setUserId(userId);
        savedArticle.setSavedAt(LocalDateTime.now());

        this.userSavedArticlesRepository.save(savedArticle);
    }

    @Transactional
    public void deleteSavedArticle(Integer userId, Integer articleId) {
        System.out.println(" user id " + userId + " article id " + articleId);
        this.userSavedArticlesRepository.deleteByUserIdAndArticleId(userId, articleId);
    }

}

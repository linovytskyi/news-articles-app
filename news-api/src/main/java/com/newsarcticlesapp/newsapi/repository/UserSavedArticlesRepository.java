package com.newsarcticlesapp.newsapi.repository;

import com.newsarcticlesapp.newsapi.model.UserSavedArticle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSavedArticlesRepository extends JpaRepository<UserSavedArticle, Long> {

    List<UserSavedArticle> findByUserId(Integer userId);

    UserSavedArticle findByUserIdAndArticleId(Integer userId, Integer articleId);

    void deleteByUserIdAndArticleId(Integer userId, Integer articleId);
}

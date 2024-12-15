package com.newsarcticlesapp.newsapi.controller;

import com.newsarcticlesapp.newsapi.model.SearchArticle;
import com.newsarcticlesapp.newsapi.model.User;
import com.newsarcticlesapp.newsapi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/article")
    public ResponseEntity<Void> saveArticle(Authentication authentication,
                                            @RequestBody Integer articleId) {
        User user = (User) authentication.getPrincipal();
        try {
            userService.saveArticle(user.getId(), articleId);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/article")
    public ResponseEntity<List<SearchArticle>> getSavedArticle(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<SearchArticle> savedArticles = userService.getUserSavedArticles(user.getId());
        return new ResponseEntity<>(savedArticles, HttpStatus.OK);
    }

    @DeleteMapping("/article/{articleId}")
    public ResponseEntity<Void> deleteArticle(Authentication authentication,
                                              @PathVariable Integer articleId) {
        User user = (User) authentication.getPrincipal();
        userService.deleteSavedArticle(user.getId(), articleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

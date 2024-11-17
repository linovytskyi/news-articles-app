package com.newsarcticlesapp.newsapi.controller;

import com.newsarcticlesapp.newsapi.model.dto.TopicsCount;
import com.newsarcticlesapp.newsapi.service.TopicService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/topic")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/top")
    public List<TopicsCount> getMostPopularTopics() {
        return topicService.getTopTopicsWithCount();
    }
}

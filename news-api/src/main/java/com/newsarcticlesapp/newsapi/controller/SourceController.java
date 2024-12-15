package com.newsarcticlesapp.newsapi.controller;

import com.newsarcticlesapp.newsapi.model.Source;
import com.newsarcticlesapp.newsapi.service.SourceService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/source")
@CrossOrigin
public class SourceController {

    private final SourceService sourceService;

    public SourceController(SourceService sourceService) {
        this.sourceService = sourceService;
    }

    @GetMapping
    public List<Source> getAllSource() {
        return this.sourceService.findAllSources();
    }
}

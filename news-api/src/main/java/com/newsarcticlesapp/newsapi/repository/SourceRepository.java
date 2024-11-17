package com.newsarcticlesapp.newsapi.repository;

import com.newsarcticlesapp.newsapi.model.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface SourceRepository extends JpaRepository<Source, Long> {

    Optional<Source> findByUrl(String name);

}

package com.newsarcticlesapp.newsapi.repository;

import com.newsarcticlesapp.newsapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

}

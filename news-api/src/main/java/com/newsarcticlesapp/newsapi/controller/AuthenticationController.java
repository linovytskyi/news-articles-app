package com.newsarcticlesapp.newsapi.controller;

import com.newsarcticlesapp.newsapi.model.AuthenticationRequest;
import com.newsarcticlesapp.newsapi.model.AuthenticationResponse;
import com.newsarcticlesapp.newsapi.model.RegisterRequest;
import com.newsarcticlesapp.newsapi.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final static Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        try {
            return ResponseEntity.ok(authenticationService.register(request));
        } catch (IllegalArgumentException e) {
            LOGGER.info("User with email {} is already registered", request.getEmail());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            LOGGER.debug("Exception occurred while registering user {}", e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request) {
        try {
            LOGGER.info("Trying to authenticate user with email - {}", request.getEmail());
            return ResponseEntity.ok(authenticationService.authenticate(request));
        } catch (NoSuchElementException e) {
            LOGGER.info("User with email {} wasn't found", request.getEmail());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOGGER.debug("Exception occurred while authenticating user {}", e.toString());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
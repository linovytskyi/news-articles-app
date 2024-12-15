package com.newsarcticlesapp.newsapi.service;

import com.newsarcticlesapp.newsapi.model.AuthenticationRequest;
import com.newsarcticlesapp.newsapi.model.AuthenticationResponse;
import com.newsarcticlesapp.newsapi.model.RegisterRequest;
import com.newsarcticlesapp.newsapi.model.User;
import com.newsarcticlesapp.newsapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final static Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);

    public AuthenticationResponse register(RegisterRequest request) {
        checkIfUserAlreadyExists(request.getEmail());

        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        repository.save(user);
        LOGGER.info("User with email {} was registered!", user.getEmail());
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void checkIfUserAlreadyExists(String email) {
        if (repository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("User with email {} already exists!");
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = findUserByEmail(request.getEmail()).orElseThrow();

        LOGGER.info("User was found!");

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword())
        );

        HashMap<String, Object> map = new HashMap<>();
        map.put("id", user.getId());

        var jwtToken = jwtService.generateToken(map, user);

        LOGGER.debug("Generated JWT token for user - {}. User authenticated", user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private Optional<User> findUserByEmail(String email) {
        LOGGER.info("Trying to find user with email - {}", email);
        return repository.findByEmail(email);
    }
}
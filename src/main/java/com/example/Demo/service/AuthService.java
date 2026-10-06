package com.example.Demo.service;

import com.example.Demo.dto.AuthRequest;
import com.example.Demo.entity.User;
import com.example.Demo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public void register(AuthRequest request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User(
                request.getUsername(),
                request.getPassword());

        userRepository.save(user);
    }

    public String login(AuthRequest request) {

        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        return jwtService.generateToken(
                user.getId(),
                user.getUsername());
    }
}

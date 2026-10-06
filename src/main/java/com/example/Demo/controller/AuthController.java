package com.example.Demo.controller;

import com.example.Demo.dto.AuthRequest;
import com.example.Demo.dto.AuthResponse;
import com.example.Demo.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody AuthRequest request) {

        authService.register(request);

        return "User registered successfully";
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {

        String token = authService.login(request);

        return new AuthResponse(token);
    }
}

package org.example.controllers;

import org.example.dto.DefaultResponse;
import org.example.dto.requests.auth.LoginRequest;
import org.example.dto.requests.auth.RegisterRequest;
import org.example.dto.responses.LoginResponse;
import org.example.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<DefaultResponse> register(@RequestBody RegisterRequest request) {
        // TODO
        return null;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        // TODO
        return null;
    }
    
}

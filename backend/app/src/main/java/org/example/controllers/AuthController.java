package org.example.controllers;

import org.example.dto.DefaultResponse;
import org.example.dto.requests.auth.LoginRequest;
import org.example.dto.requests.auth.RegisterRequest;
import org.example.dto.responses.auth.LoginResponse;
import org.example.models.User;
import org.example.security.JwtService;
import org.example.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<DefaultResponse> register(@RequestBody @Valid RegisterRequest request) {
        userService.registerUser(request);
        return ResponseEntity.ok(new DefaultResponse(HttpStatus.OK, "OK"));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        User user = userService.authUser(request);
        String token = jwtService.generateToken(user.getId());
        return ResponseEntity.ok(new LoginResponse(HttpStatus.OK, "OK", token, user.getUsername(), user.getPrivilegeLevel()));
    }
    
}

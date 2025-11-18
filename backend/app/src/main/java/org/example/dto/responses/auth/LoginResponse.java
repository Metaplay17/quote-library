package org.example.dto.responses.auth;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private HttpStatus status;
    private String message;
    private String token;
    private String username;
    private Byte privilegeLevel;
}

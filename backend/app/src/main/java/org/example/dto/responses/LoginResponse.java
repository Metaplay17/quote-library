package org.example.dto.responses;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponse {
    private HttpStatus status;
    private String message;
    private String username;
    private Byte privilegeLevel;
    private String token;
}

package org.example.dto.responses.user;

import org.example.dto.ProfileDto;
import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileResponse {
    private HttpStatus status;
    private String message;
    private ProfileDto profile;
}

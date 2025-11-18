package org.example.dto.requests.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {
    @NotNull
    @NotBlank
    @Size(min = 3)
    private String username;

    @NotNull
    @NotBlank
    @Size(min = 8)
    private String password;
}

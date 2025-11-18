package org.example.dto.requests.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank
    @NotNull
    @Size(min = 3)
    private String username;

    @NotBlank
    @NotNull
    @Size(min = 8)
    private String password;
}

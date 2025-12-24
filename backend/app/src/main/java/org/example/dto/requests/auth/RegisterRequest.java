package org.example.dto.requests.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Имя пользователя не может быть пустым")
    @NotNull(message = "Имя пользователя должно быть указано")
    @Size(min = 3, max = 25, message = "Имя пользователя должно быть от 3 до 25 символов в длину")
    private String username;

    @NotBlank(message = "Пароль не может быть пустым")
    @NotNull(message = "Пароль должен быть указан")
    @Size(min = 8, message = "Длина пароля от 8 символов")
    private String password;
}

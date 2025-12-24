package org.example.dto.requests.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {
    @NotNull(message = "Имя пользователя должно быть указано")
    @NotBlank(message = "Имя пользователя не может быть пустым")
    @Size(min = 3, max = 25, message = "Длина имени пользователя - от 3 до 25 символов")
    private String username;

    @NotNull(message = "Пароль должен быть указан")
    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 8, message = "длина пароля - от 8 символов")
    private String password;
}

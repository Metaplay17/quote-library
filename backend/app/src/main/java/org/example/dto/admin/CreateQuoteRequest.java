package org.example.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateQuoteRequest {
    @NotNull(message = "Текст цитаты должен быть указан")
    @NotBlank(message = "Текст цитаты не может быть пустым")
    private String text;

    @NotNull(message = "Проверьте указанного автора")
    @Positive(message = "ID автора должно быть положительным числом")
    private Integer authorId;

    @Size(max = 500, message = "Длина контекста до 200 символов")
    private String context;

    @NotEmpty(message = "Должен быть указан минимум 1 тег")
    private String[] tags;
}
package org.example.dto.requests.quotes;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindByPatternAndAuthorsAndTagsRequest {
    @NotNull(message = "Паттерн поиска должен быть задан")
    @Size(max = 100, message = "Длина паттерна поиска - до 100 символов")
    private String pattern;

    @NotNull(message = "Список ID авторов должен быть задан (может быть пустым)")
    private Integer[] authorsId;

    @NotNull(message = "Список ID тегов должен быть задан (может быть пустым)")
    private Integer[] tagsId;

    @PositiveOrZero(message = "Стартовый индекс не может быть отрицательным")
    @NotNull(message = "Стартовый индекс должен быть задан")
    private Integer startIndex;
}

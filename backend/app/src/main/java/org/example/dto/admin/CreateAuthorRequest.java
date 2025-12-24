package org.example.dto.admin;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateAuthorRequest {
    @NotNull(message = "Имя автора должно быть указано")
    @NotEmpty(message = "Имя автора не может быть пустым")
    private String name;
}

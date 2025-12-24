package org.example.dto.admin;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateTagRequest {
    @NotNull(message = "Название тега должно быть указано")
    @NotEmpty(message = "Название тега не может быть пустым")
    private String name;
}

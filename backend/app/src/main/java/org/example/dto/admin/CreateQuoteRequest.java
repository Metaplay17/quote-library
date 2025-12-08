package org.example.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateQuoteRequest {
    @NotNull
    @NotBlank
    private String text;

    @NotNull
    @Positive
    private Integer authorId;
    private String context;

    @NotEmpty
    private String[] tags;
}

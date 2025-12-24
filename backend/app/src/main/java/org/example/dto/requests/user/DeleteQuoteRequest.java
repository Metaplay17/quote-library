package org.example.dto.requests.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteQuoteRequest {
    @NotNull(message = "ID цитаты должен быть задан")
    @Positive(message = "ID цитаты - положительное число")
    private Integer quoteId;
}

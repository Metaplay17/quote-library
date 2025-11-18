package org.example.dto.responses.quotes;

import java.util.List;

import org.springframework.http.HttpStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthorQuotesResponse {
    private HttpStatus status;
    private String message;
    private List<QuoteDto> quotes;
}

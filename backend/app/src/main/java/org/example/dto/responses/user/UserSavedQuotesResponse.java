package org.example.dto.responses.user;

import java.util.List;

import org.example.dto.responses.quotes.QuoteDto;
import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSavedQuotesResponse {
    private HttpStatus status;
    private String message;
    private List<QuoteDto> quotes;
    private Integer startIndex;
}

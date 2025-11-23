package org.example.dto.responses.authors;

import java.util.List;

import org.example.dto.AuthorDto;
import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAllAuthorsResponse {
    private HttpStatus status;
    private String message;
    private List<AuthorDto> authors;
}

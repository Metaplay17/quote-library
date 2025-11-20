package org.example.dto.responses.quotes;

import java.util.List;

import org.example.dto.TagDto;
import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TagsListResponse {
    private HttpStatus status;
    private String message;
    private List<TagDto> tags;
}

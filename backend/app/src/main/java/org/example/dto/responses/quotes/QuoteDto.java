package org.example.dto.responses.quotes;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuoteDto {
    private Integer id;
    private Integer authorId;
    private List<TagDto> tags;
    private String text;
    private String context;
}

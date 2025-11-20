package org.example.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuoteDto {
    private Integer id;
    private String author;
    private List<TagDto> tags;
    private String text;
    private String context;
}

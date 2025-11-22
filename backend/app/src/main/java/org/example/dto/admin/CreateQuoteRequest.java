package org.example.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateQuoteRequest {
    private String text;
    private Integer authorId;
    private String context;
    private String[] tags;
}

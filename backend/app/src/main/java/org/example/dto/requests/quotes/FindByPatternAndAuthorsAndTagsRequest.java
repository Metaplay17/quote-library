package org.example.dto.requests.quotes;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindByPatternAndAuthorsAndTagsRequest {
    @NotNull
    private String pattern;

    @NotNull
    private Integer[] authorsId;

    @NotNull
    private Integer[] tagsId;

    @PositiveOrZero
    @NotNull
    private Integer startIndex;
}

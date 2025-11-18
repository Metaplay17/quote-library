package org.example.dto.requests.quotes;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindByAuthorsAndTagsRequest {
    @NotNull
    private List<Integer> authorsId;

    @NotNull
    private List<Integer> tagsId;

    @PositiveOrZero
    private Integer startIndex;
}

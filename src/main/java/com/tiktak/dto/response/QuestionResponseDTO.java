package com.tiktak.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class QuestionResponseDTO {

    @NotNull
    private Long questionId;

    private boolean response;

    private List<String> imageUrls;

    private String description;

}
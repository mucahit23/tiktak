package com.tiktak.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class QuestionResponseInfo implements Serializable {

    private Long id;
    private Long carId;
    private int expertiseNo;
    private QuestionInfo questionInfo; // Replacing questionId and questionText
    private boolean response;
    private String description;
    private Set<String> responseImageUrls; // Assuming you want to transfer image URLs

    // Standard getters and setters
}

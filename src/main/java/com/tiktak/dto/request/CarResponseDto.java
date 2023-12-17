package com.tiktak.dto.request;

import com.tiktak.dto.response.QuestionResponseDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class CarResponseDto {

    @NotNull
    private Long carId;

    @NotEmpty
    private List<QuestionResponseDTO> questionResponses;

}
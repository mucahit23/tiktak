package com.tiktak.validator;


import com.tiktak.config.ImageProperties;
import com.tiktak.dto.request.CarResponseDto;
import com.tiktak.dto.response.QuestionResponseDTO;
import com.tiktak.exception.BadRequestException;
import com.tiktak.repos.QuestionRepository;
import com.tiktak.service.MessageService;
import com.tiktak.util.LogBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QuestionResponseValidatorTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private ImageProperties imageProperties;

    @Mock
    private LogBean logBean;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private QuestionResponseValidator questionResponseValidator;

    @BeforeEach
    void setUp() {

    }

    @Test
    void validateQuestionResponses_InvalidImageResponse_ExceptionThrown() {

        CarResponseDto carResponseDto = createCarResponseDtoWithInvalidImageCount();
        assertThrows(BadRequestException.class,
                () -> questionResponseValidator.validateQuestionResponses(carResponseDto));
    }

    @Test
    void validateQuestionResponses_ValidQuestionIds_NoExceptionThrown() {

        CarResponseDto carResponseDto = createValidCarResponseDto();
        when(imageProperties.getMinCount()).thenReturn(1);
        when(imageProperties.getMaxCount()).thenReturn(3);
        when(questionRepository.getValidQuestionCount(anyList())).thenReturn(1);
        assertDoesNotThrow(() -> questionResponseValidator.validateQuestionResponses(carResponseDto));
    }

    @Test
    void validateQuestionResponses_InvalidQuestionIds_ExceptionThrown() {

        CarResponseDto carResponseDto = createValidCarResponseDto();

        assertThrows(BadRequestException.class,
                () -> questionResponseValidator.validateQuestionResponses(carResponseDto));
    }

    private CarResponseDto createValidCarResponseDto() {
        CarResponseDto dto = new CarResponseDto();
        dto.setCarId(1L);
        QuestionResponseDTO questionResponse = new QuestionResponseDTO();
        questionResponse.setQuestionId(1L);
        questionResponse.setResponse(true);
        questionResponse.setImageUrls(Arrays.asList("url1", "url2"));
        dto.setQuestionResponses(Collections.singletonList(questionResponse));
        return dto;
    }

    private CarResponseDto createCarResponseDtoWithInvalidImageCount() {
        CarResponseDto dto = new CarResponseDto();
        QuestionResponseDTO questionResponse = new QuestionResponseDTO();
        questionResponse.setQuestionId(1L);
        questionResponse.setImageUrls(Collections.emptyList()); // Invalid image count
        dto.setQuestionResponses(Collections.singletonList(questionResponse));
        return dto;
    }

}

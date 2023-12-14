package com.tiktak.service;

import com.tiktak.dto.request.CarResponseDto;
import com.tiktak.dto.response.QuestionResponseInfo;
import com.tiktak.entity.QuestionResponse;
import com.tiktak.mapper.QuestionResponseMapper;
import com.tiktak.repos.QuestionResponseRepository;
import com.tiktak.validator.QuestionResponseValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuestionResponseServiceTest {

    @Mock
    private QuestionResponseMapper questionResponseMapper;

    @Mock
    private QuestionResponseRepository questionResponseRepository;

    @Mock
    private QuestionResponseValidator questionResponseValidator;

    @InjectMocks
    private QuestionResponseService questionResponseService;

    @Test
    void getLastExpertiseByCarId_WhenResponsesExist() {
        // Arrange
        Long carId = 1L;
        List<QuestionResponse> mockResponses = List.of(new QuestionResponse());
        when(questionResponseRepository.findWithHighestExpertiseNo(carId)).thenReturn(mockResponses);

        List<QuestionResponseInfo> mockInfoList = List.of(new QuestionResponseInfo());
        when(questionResponseMapper.questionResponseListToQuestionResponseDtoList(mockResponses)).thenReturn(mockInfoList);

        // Act
        List<QuestionResponseInfo> result = questionResponseService.getLastExpertiseByCarId(carId);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(mockInfoList, result);
    }

    @Test
    void getLastExpertiseByCarId_WhenNoResponses() {
        // Arrange
        Long carId = 1L;
        when(questionResponseRepository.findWithHighestExpertiseNo(carId)).thenReturn(Collections.emptyList());

        // Act
        List<QuestionResponseInfo> result = questionResponseService.getLastExpertiseByCarId(carId);

        // Assert
        assertNull(result);
    }


    @Test
    public void testCreateCardResponse() {
        // Arrange
        CarResponseDto carResponseDto = new CarResponseDto(); // Populate with test data
        Set<QuestionResponse> questionResponses = Collections.singleton(new QuestionResponse());
        List<QuestionResponseInfo> expectedResponse = Collections.singletonList(new QuestionResponseInfo()); // Mocked response

        when(questionResponseMapper.carResponseDTOToQuestionResponseList(any())).thenReturn(questionResponses);
        when(questionResponseMapper.questionResponseListToQuestionResponseDtoList(any())).thenReturn(expectedResponse);

        // Act
        List<QuestionResponseInfo> actualResponse = questionResponseService.createCardResponse(carResponseDto);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        verify(questionResponseValidator).validateQuestionResponses(carResponseDto);
        verify(questionResponseRepository).saveAll(questionResponses);
    }

}

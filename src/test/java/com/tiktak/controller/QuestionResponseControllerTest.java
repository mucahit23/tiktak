package com.tiktak.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiktak.dto.request.CarResponseDto;
import com.tiktak.dto.response.QuestionResponseDTO;
import com.tiktak.dto.response.QuestionResponseInfo;
import com.tiktak.service.QuestionResponseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
class QuestionResponseControllerTest {

    @Mock
    private QuestionResponseService questionResponseService;

    @InjectMocks
    private QuestionResponseController questionResponseController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(questionResponseController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createCardResponse_GetMethod() throws Exception {
        Long carId = 12345L;
        List<QuestionResponseInfo> mockResponse = Arrays.asList(new QuestionResponseInfo()); // Mock response
        when(questionResponseService.getLastExpertiseByCarId(carId)).thenReturn(mockResponse);

        mockMvc.perform(get("/responses/{carId}", carId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.content").isArray());

        verify(questionResponseService).getLastExpertiseByCarId(carId);
    }

    @Test
    void createCardResponse_PostMethod() throws Exception {
        // Arrange
        CarResponseDto carResponseDto = createCarResponseDto();
        List<QuestionResponseInfo> mockResponse = Arrays.asList(new QuestionResponseInfo());
        when(questionResponseService.createCardResponse(any(CarResponseDto.class))).thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(post("/responses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carResponseDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

        verify(questionResponseService).createCardResponse(any(CarResponseDto.class));
    }

    private CarResponseDto createCarResponseDto() {
        CarResponseDto dto = new CarResponseDto();
        dto.setCarId(12345L);
        dto.setQuestionResponses(Arrays.asList(
                new QuestionResponseDTO(1L, true, Arrays.asList("https://example.com/image145451.jpg", "https://example.com/image2543.jpg"), "Description for question 1"),
                new QuestionResponseDTO(2L, false, Arrays.asList("https://example.com/image221.jpg", "https://example.com/image21.jpg"), "Description for question 2"),
                new QuestionResponseDTO(3L, true, Collections.singletonList("https://example.com/image42.jpg"), "Description for question 3")
        ));
        return dto;
    }
}

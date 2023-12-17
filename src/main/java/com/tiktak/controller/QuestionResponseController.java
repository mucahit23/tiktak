package com.tiktak.controller;

import com.tiktak.dto.request.CarResponseDto;
import com.tiktak.dto.response.QuestionInfo;
import com.tiktak.dto.response.QuestionResponseInfo;
import com.tiktak.dto.response.base.GenericResponse;
import com.tiktak.service.QuestionResponseService;
import com.tiktak.util.TiktakUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/responses")
@RequiredArgsConstructor
public class QuestionResponseController {

    private final QuestionResponseService questionResponseService;

    @GetMapping("/questions/{carId}")
    public GenericResponse<List<QuestionInfo>> getLastExpertiseQuestionsByCarId(@PathVariable("carId") final Long carId) {

        final var lastExpertiseQuestions = questionResponseService.findQuestionOfLastExpertiseByCardId(carId);

        return TiktakUtils.generateGenericResponse(lastExpertiseQuestions);
    }

    @GetMapping("/{carId}")
    public GenericResponse<List<QuestionResponseInfo>> getExpertiseByCarId(@PathVariable("carId") final Long carId) {

        final var lastExpertise = questionResponseService.getLastExpertiseByCarId(carId);

        return TiktakUtils.generateGenericResponse(lastExpertise);
    }

    @PostMapping
    public GenericResponse<List<QuestionResponseInfo>> createCardResponse(@RequestBody @Valid final CarResponseDto carResponseDto) {

        final var createdQuestionResponseInfo = questionResponseService.createCardResponse(carResponseDto);

        return TiktakUtils.generateGenericResponse(createdQuestionResponseInfo);
    }
}

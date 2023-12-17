package com.tiktak.controller;

import com.tiktak.dto.response.QuestionInfo;
import com.tiktak.dto.response.base.GenericResponse;
import com.tiktak.service.QuestionService;
import com.tiktak.util.TiktakUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping
    public GenericResponse<List<QuestionInfo>> getLastExpertiseQuestionsByCarId() {

        final var questions = questionService.findAll();

        return TiktakUtils.generateGenericResponse(questions);
    }

}

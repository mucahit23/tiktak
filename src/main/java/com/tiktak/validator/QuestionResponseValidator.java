package com.tiktak.validator;

import com.tiktak.config.ImageProperties;
import com.tiktak.dto.request.CarResponseDto;
import com.tiktak.dto.request.QuestionResponseDTO;
import com.tiktak.exception.BadRequestException;
import com.tiktak.repos.QuestionRepository;
import com.tiktak.service.MessageService;
import com.tiktak.util.LogBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class QuestionResponseValidator {

    private final QuestionRepository questionRepository;
    private final ImageProperties imageProperties;
    private final LogBean logBean;
    private final MessageService messageService;

    public void validateQuestionResponses(CarResponseDto carResponseDto) {

        if (!isValidResponseImages(carResponseDto)) {
            log.error("(traceId({})]) invalid response images for car-id : {}", logBean.getTraceId(), carResponseDto.getCarId());
            throw new BadRequestException(messageService.getLocaleMessage("error.message.invalid.image.size", imageProperties.getMinCount() + "", imageProperties.getMaxCount() + ""));
        }

        final var questionIds = getQuestionIds(carResponseDto);

        if (CollectionUtils.isEmpty(questionIds)) {
            throw new BadRequestException(messageService.getLocaleMessage("error.message.invalid.question.size"));
        }

        //Tüm soru id lerinden herbiri fiziksel bir kayda denk gelmeli.
        if (!isValidQuestions(questionIds)) {
            throw new BadRequestException(messageService.getLocaleMessage("error.message.undefined.question"));
        }
    }

    private boolean isValidResponseImages(final CarResponseDto carResponseDto) {

        for (QuestionResponseDTO qr : carResponseDto.getQuestionResponses()) {
            if (qr.isResponse() && (
                    CollectionUtils.isEmpty(qr.getImageUrls())
                            || qr.getImageUrls().size() < imageProperties.getMinCount()
                            || qr.getImageUrls().size() > imageProperties.getMaxCount())) {
                return false;
            } else if (!qr.isResponse()) {
                if (!CollectionUtils.isEmpty(qr.getImageUrls())) {
                    return false;
                }
            }

        }
        return true;
    }

    private boolean isValidQuestions(final List<Long> questionIds) {
        final var validCount = questionRepository.getValidQuestionCount(questionIds);

        return questionIds.size() == validCount;
    }

    private List<Long> getQuestionIds(final CarResponseDto carResponseDto) {
        if (carResponseDto.getQuestionResponses() == null) {
            return Collections.emptyList();
        }

        return carResponseDto.getQuestionResponses().stream()
                .map(QuestionResponseDTO::getQuestionId)
                .collect(Collectors.toList());
    }


}

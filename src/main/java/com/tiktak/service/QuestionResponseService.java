package com.tiktak.service;

import com.tiktak.dto.request.CarResponseDto;
import com.tiktak.dto.response.QuestionInfo;
import com.tiktak.dto.response.QuestionResponseInfo;
import com.tiktak.entity.QuestionResponse;
import com.tiktak.mapper.QuestionMapper;
import com.tiktak.mapper.QuestionResponseMapper;
import com.tiktak.repos.QuestionResponseRepository;
import com.tiktak.validator.QuestionResponseValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class QuestionResponseService {

    private final QuestionResponseMapper questionResponseMapper;
    private final QuestionMapper questionMapper;
    private final QuestionResponseRepository questionResponseRepository;
    private final QuestionResponseValidator questionResponseValidator;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    @Cacheable(value = "last-expertise", key = "#carId")
    public List<QuestionResponseInfo> getLastExpertiseByCarId(final Long carId) {

        final var responses = questionResponseRepository.findWithHighestExpertiseNo(carId);

        if (CollectionUtils.isEmpty(responses)) {
            return null;
        }

        return questionResponseMapper.questionResponseListToQuestionResponseDtoList(responses);
    }

    /*
    1 - validate carResponseDto
    2 - convert questionResponse request to db elements
    3 - set extra info for questionResponse. 3.1 - carId 3.2 - evaulated expertiseNo
     */
    @CacheEvict(value = {"questions-by-carId", "last-expertise"} , key = "#carResponseDto.carId")
    public List<QuestionResponseInfo> createCardResponse(final CarResponseDto carResponseDto) {
        // 1 -
        questionResponseValidator.validateQuestionResponses(carResponseDto);

        // 2 -
        final var questionResponses = questionResponseMapper.carResponseDTOToQuestionResponseList(carResponseDto.getQuestionResponses());

        // 3 -
        setExpertiseInfo(questionResponses, carResponseDto.getCarId());

        questionResponseRepository.saveAll(questionResponses);

        return questionResponseMapper.questionResponseListToQuestionResponseDtoList(new ArrayList<>(questionResponses));
    }

    @Cacheable(value = "questions-by-carId", key = "#carId")
    public List<QuestionInfo> findQuestionOfLastExpertiseByCardId(final Long carId) {
        final var questions = questionResponseRepository.findQuestionOfLastExpertiseByCardId(carId);

        return questionMapper.fromEntityListToDtoList(questions);
    }


    private void setExpertiseInfo(Set<QuestionResponse> questionResponses, Long carId) {
        final int expertiseNo = getNextExpertiseNo(carId);
        questionResponses.forEach(qr -> {
            qr.setCarId(carId);
            qr.setExpertiseNo(expertiseNo);
        });
    }

    /*
    if there is already n expertise for relevant carId, increase expertise no; else set expertise no : 1
     */
    private int getNextExpertiseNo(final Long carId) {
        if (!questionResponseRepository.existsByCarId(carId)) {
            return 1;
        }
        return questionResponseRepository.getMaxExpertiseNoByCarId(carId) + 1;
    }

}

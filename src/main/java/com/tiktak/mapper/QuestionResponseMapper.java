package com.tiktak.mapper;

import com.tiktak.dto.response.QuestionResponseDTO;
import com.tiktak.dto.response.QuestionResponseInfo;
import com.tiktak.entity.Question;
import com.tiktak.entity.QuestionResponse;
import com.tiktak.entity.ResponseImage;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class QuestionResponseMapper {

    public abstract List<QuestionResponseInfo> questionResponseListToQuestionResponseDtoList(final List<QuestionResponse> questionResponses);

    @Mapping(target = "questionInfo.id", source = "question.id")
    @Mapping(target = "questionInfo.questionText", source = "question.questionText")
    @Mapping(target = "responseImageUrls", expression = "java(mapImageUrls(questionResponse.getResponseImageSet()))")
    public abstract QuestionResponseInfo questionResponseToQuestionResponseDto(final QuestionResponse questionResponse);

    protected Set<String> mapImageUrls(Set<ResponseImage> responseImages) {
        if (responseImages == null) {
            return Collections.emptySet();
        }
        return responseImages.stream()
                .map(ResponseImage::getImageUrl) // Assuming ResponseImage has a method getImageUrl
                .collect(Collectors.toSet());
    }

    public abstract Set<QuestionResponse> carResponseDTOToQuestionResponseList(final List<QuestionResponseDTO> questionResponseDTOList);

    @Mapping(target = "question", expression = "java(mapQuestion(questionResponseDTO))")
    public abstract QuestionResponse questionResponseDTOToQuestionResponse(QuestionResponseDTO questionResponseDTO);

    @AfterMapping
    protected void linkImages(@MappingTarget QuestionResponse questionResponse, QuestionResponseDTO questionResponseDTO) {
        Set<ResponseImage> images = mapImageSet(questionResponseDTO, questionResponse);
        if (images != null) {
            questionResponse.getResponseImageSet().clear();
            questionResponse.getResponseImageSet().addAll(images);
        }
    }

    protected Set<ResponseImage> mapImageSet(QuestionResponseDTO questionResponseDTO, QuestionResponse questionResponse) {
        if (questionResponseDTO == null
                || questionResponseDTO.getImageUrls() == null
                || CollectionUtils.isEmpty(questionResponseDTO.getImageUrls())) {
            return null;
        }

        return questionResponseDTO.getImageUrls().stream()
                .map(imgUrl -> {
                    ResponseImage responseImage = ResponseImage.builder().imageUrl(imgUrl).build();
                    responseImage.setQuestionResponse(questionResponse);
                    return responseImage;
                })
                .collect(Collectors.toSet());
    }

    protected Question mapQuestion(QuestionResponseDTO questionResponseDTO) {
        if (questionResponseDTO == null || questionResponseDTO.getQuestionId() == null) {
            return null;
        }
        return new Question(questionResponseDTO.getQuestionId());
    }
}

package com.tiktak.mapper;

import com.tiktak.dto.response.QuestionResponseInfo;
import com.tiktak.entity.Question;
import com.tiktak.entity.QuestionResponse;
import com.tiktak.entity.ResponseImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class QuestionResponseMapperTest {

    private QuestionResponseMapper questionResponseMapper;

    @BeforeEach
    void setup() {
        questionResponseMapper = Mappers.getMapper(QuestionResponseMapper.class);
    }

    @Test
    void testQuestionResponseToQuestionResponseDto() {

        QuestionResponse questionResponse = createQuestionResponse();

        QuestionResponseInfo dto = questionResponseMapper.questionResponseToQuestionResponseDto(questionResponse);

        assertNotNull(dto);
        assertEquals(questionResponse.getQuestion().getId(), dto.getQuestionInfo().getId());
        assertEquals(questionResponse.getQuestion().getQuestionText(), dto.getQuestionInfo().getQuestionText());

    }

    private QuestionResponse createQuestionResponse() {
        QuestionResponse questionResponse = new QuestionResponse();
        Question question = new Question(1L, "Sample question text");
        questionResponse.setQuestion(question);
        questionResponse.setResponseImageSet(createResponseImages());


        return questionResponse;
    }

    private Set<ResponseImage> createResponseImages() {
        ResponseImage image = ResponseImage.builder().imageUrl("https://example.com/image.jpg").build();
        return Collections.singleton(image);
    }
}

package com.tiktak.mapper;

import com.tiktak.dto.response.QuestionInfo;
import com.tiktak.entity.Question;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class QuestionMapper {

    public abstract List<QuestionInfo> fromEntityListToDtoList(final List<Question> questions);

    public abstract QuestionInfo fromEntityToDto(final Question question);

}

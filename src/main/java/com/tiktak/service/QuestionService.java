package com.tiktak.service;

import com.tiktak.dto.response.QuestionInfo;
import com.tiktak.entity.Question;
import com.tiktak.mapper.QuestionMapper;
import com.tiktak.repos.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    @Cacheable("questions-cache")
    public List<QuestionInfo> findAll() {
        List<Question> all = questionRepository.findAll();
        return questionMapper.fromEntityListToDtoList(all);
    }

}

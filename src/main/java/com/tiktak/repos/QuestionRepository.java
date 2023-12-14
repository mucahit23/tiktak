package com.tiktak.repos;

import com.tiktak.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("select count(qr.id) from Question qr where qr.id in :questionIds")
    int getValidQuestionCount(@Param("questionIds") List<Long> questionIds);

}
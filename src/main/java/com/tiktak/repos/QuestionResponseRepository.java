package com.tiktak.repos;

import com.tiktak.entity.Question;
import com.tiktak.entity.QuestionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionResponseRepository extends JpaRepository<QuestionResponse, Long> {

    boolean existsByCarId(Long carId);

    @Query("select qr.expertiseNo from QuestionResponse qr where carId =:carId order by qr.expertiseNo desc limit 1")
    Integer getMaxExpertiseNoByCarId(@Param("carId") final Long carId);

    @Query("SELECT qr FROM QuestionResponse qr join fetch qr.question q join fetch qr.responseImageSet images  WHERE qr.carId =:carId and qr.expertiseNo = (SELECT MAX(qr2.expertiseNo) FROM QuestionResponse qr2 WHERE qr2.carId = qr.carId)")
    List<QuestionResponse> findWithHighestExpertiseNo(@Param("carId") final Long carId);

    @Query("SELECT q FROM QuestionResponse qr join qr.question q WHERE qr.carId =:carId and qr.expertiseNo = (SELECT MAX(qr2.expertiseNo) FROM QuestionResponse qr2 WHERE qr2.carId = qr.carId)")
    List<Question> findQuestionOfLastExpertiseByCardId(@Param("carId") final Long carId);
}
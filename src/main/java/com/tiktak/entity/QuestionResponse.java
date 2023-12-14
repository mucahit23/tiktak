package com.tiktak.entity;

import com.tiktak.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Entity
@Table(name = "question_responses")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString
public class QuestionResponse extends BaseEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "gen_seq_question_response",
            sequenceName = "seq_question_response",
            allocationSize = 100,
            initialValue = 10000
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_seq_question_response")
    private Long id;

    @Column(name = "car_id")
    private Long carId;

    //For each car show expertize order, for the first time it is 1, and increase by 1 for each new expertize
    @Column(name = "expertise_no")
    private int expertiseNo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    @ToString.Exclude
    private Question question;

    @OneToMany(mappedBy = "questionResponse", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 1000)
    @ToString.Exclude
    private Set<ResponseImage> responseImageSet = new HashSet<>();

    @Column(name = "response")
    private boolean response;

    @Column(name = "description")
    private String description;

    public QuestionResponse(Long id) {
        this.id = id;
    }

    public void addResponseImage(ResponseImage responseImage) {
        responseImageSet.add(responseImage);
        responseImage.setQuestionResponse(this);
    }

    public void removeResponseImage(ResponseImage responseImage) {
        responseImageSet.remove(responseImage);
        responseImage.setQuestionResponse(null);
    }

}

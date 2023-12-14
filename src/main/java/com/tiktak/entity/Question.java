package com.tiktak.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@NoArgsConstructor
@Entity
@Table(name = "questions")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Question {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "gen_seq_question",
            sequenceName = "seq_question",
            allocationSize = 100,
            initialValue = 10000
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_seq_question")
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String questionText;

    public Question(Long id) {
        this.id = id;
    }

}

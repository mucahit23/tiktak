package com.tiktak.entity;

import com.tiktak.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@NoArgsConstructor
@Entity
@Table(name = "response_images")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class ResponseImage extends BaseEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "gen_seq_response_image",
            sequenceName = "seq_response_image",
            allocationSize = 100,
            initialValue = 10000
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_seq_response_image")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_response_id", nullable = false)
    @ToString.Exclude
    private QuestionResponse questionResponse;

    @Column(name = "image_url")
    private String imageUrl;

}

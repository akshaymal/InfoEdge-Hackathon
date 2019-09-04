package com.nnacres.assessment.entity;

import lombok.*;

import javax.persistence.*;

/**
 * Created by Vishal on 18/12/17.
 */

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CONTEST_QUESTION")
@ToString(doNotUseGetters = true)
public class ContestQuestion extends BaseEntity{

    @Column(name = "QUESTION_ID", nullable = false)
    private Long questionId;

    @Column(name = "QUESTION_TITLE", nullable = false)
    private String questionTitle;

    @Column(name = "CONTEST_ID", nullable = false)
    private Long contestId;

    @Column(name = "POINTS")
    private double points;

    @Column(name = "NEGATIVE_POINTS")
    private double negativePoints;

    @ManyToOne
    @JoinColumn(name = "CONTEST_ID", referencedColumnName = "ID", insertable = false, updatable =
        false)
    private Contest contest;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID", referencedColumnName = "ID", insertable = false, updatable =
        false)
    private Question question;
}

package com.nnacres.assessment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "QUESTION_RESPONSE")
@ToString(doNotUseGetters = true)
public class QuestionResponse extends BaseEntity {

    @Column(name = "CONTEST_ID")
    private long contestId;

    @Column(name = "QUESTION_ID")
    private long questionId;

    @Column(name = "TIME_TAKEN")
    private long timeTaken;

    @Column(name = "ANSWER_GIVEN")
    private String answerGiven;

    @Column(name = "MARKS")
    private int marks;

    @Column(name = "LANGUAGE")
    private int language;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "program", columnDefinition = "text")
    private String program;
}

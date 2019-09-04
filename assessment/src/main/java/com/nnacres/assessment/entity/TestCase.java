package com.nnacres.assessment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "TEST_CASE")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(doNotUseGetters = true)
public class TestCase extends BaseEntity{

    @Column(name = "INPUT", nullable = false)
    private String input;

    @Column(name = "OUTPUT", nullable = false)
    private String output;


    @Column(name = "IS_SAMPLE")
    private boolean isSample;

    @Column(name = "QUESTION_ID", nullable = false)
    private Long questionId;

    @Column(name = "MARKS")
    private int marks;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "QUESTION_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    private Question question;
}

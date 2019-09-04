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
@Table(name = "OPTION")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(doNotUseGetters = true, exclude = "question")
public class Option extends BaseEntity{


    @Column(name = "VALUE", columnDefinition = "text", nullable = false)
    private String value;

    @Column(name = "QUESTION_ID", nullable = false)
    private Long questionId;

    @Column(name = "OPTION_ORDER", nullable = false)
    private Integer order;

    @JsonIgnore
    @Column(name = "IS_CORRECT_OPTION")
    private boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    private Question question;

}

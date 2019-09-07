package com.nnacres.assessment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nnacres.assessment.enums.DifficultyLevel;
import com.nnacres.assessment.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString(doNotUseGetters = true)
public class QuestionCriteriaDTO implements Serializable{

    private static final long serialVersionUID = -3163364098584613407L;
    private QuestionType type;
    private DifficultyLevel difficultyLevel;
    private String categoryName;
    private int questionCount;
}

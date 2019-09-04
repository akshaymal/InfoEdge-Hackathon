package com.nnacres.assessment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nnacres.assessment.entity.QuestionResponse;
import com.nnacres.assessment.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import java.io.Serializable;
import java.util.List;

/**
 * Created by Neelasha on 20/12/17.
 */

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString(doNotUseGetters = true)
public class QuestionResponseDTO implements Serializable {

	private static final long serialVersionUID = 7263055990930015756L;
	private Long id;
	private long contestId;
	private long questionId;
	private long timeTaken;
	private List<Long> answerGiven;
	private int marks;
	private int negativeMarks;
	private int languageId;
	private QuestionType questionType;
	private String program;
	private Long userId;

	@JsonIgnore
    public QuestionResponseDTO convertQuestionResponse(QuestionResponse questionResponse) {
        return QuestionResponseDTO.builder().id(questionResponse.getId())
            .contestId(questionResponse.getContestId()).questionId(questionResponse.getQuestionId())
            .timeTaken(questionResponse.getTimeTaken()).marks(questionResponse.getMarks()).build();
    }

}

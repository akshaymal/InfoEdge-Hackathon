package com.nnacres.assessment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nnacres.assessment.entity.ContestQuestion;
import com.nnacres.assessment.entity.Option;
import com.nnacres.assessment.entity.Question;
import com.nnacres.assessment.entity.TestCase;
import com.nnacres.assessment.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;



@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString(doNotUseGetters = true)
public class ContestQuestionDTO implements Serializable{

    private static final long serialVersionUID = -3991991175818019353L;
    private Long id;
    private Long questionId;
    private String questionTitle;
    private String statement;
    private Long contestId;
    private double points;
    private String questionType;
    private double negativePoints;
    private Set<OptionDto> optionDtos;
    private Set<TestCaseDto> testcaseDtos;


    @JsonIgnore
    public ContestQuestionDTO convertContestQuestion(ContestQuestion contestQuestion) {
        Question question = contestQuestion.getQuestion();
        Set<TestCaseDto> testCaseDtoSet = new HashSet<>();
        Set<OptionDto> optionDtoSet = new HashSet<>();
        String questionText = "";

        if(question != null) {
            questionText = question.getText();

            QuestionType questionType = question.getType();
            if (questionType == QuestionType.CODING) {
                Set<TestCase> testCases = question.getTestCases();

                if (CollectionUtils.isNotEmpty(testCases)) {
                    for (TestCase testCase : testCases) {
                        if (testCase.isSample()) {
                            TestCaseDto testCaseDto = new TestCaseDto();
                            testCaseDtoSet.add(testCaseDto.convertTestCase(testCase));
                        }
                    }

                }
            } else {
                Set<Option> options = question.getOptions();
                if (CollectionUtils.isNotEmpty(options)) {
                    for (Option option : options) {
                        OptionDto optionDto = new OptionDto();
                        optionDtoSet.add(optionDto.convertOptionForUser(option));
                    }

                }

            }
            return ContestQuestionDTO.builder().id(contestQuestion.getId()).testcaseDtos(testCaseDtoSet)
                .optionDtos(optionDtoSet).contestId(contestQuestion.getContestId()).statement(questionText)
                .questionId(contestQuestion.getQuestionId()).points(contestQuestion.getPoints())
                .negativePoints(contestQuestion.getNegativePoints()).questionType(questionType.name())
                .questionTitle(contestQuestion.getQuestionTitle()).build();
        }
        return null;
    }

}

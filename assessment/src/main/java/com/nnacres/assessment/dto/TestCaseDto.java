package com.nnacres.assessment.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nnacres.assessment.entity.TestCase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString(doNotUseGetters = true)
public class TestCaseDto {

    private String input;
    private String output;
    private boolean isSample;
    private Long questionId;
    private int marks;

    @JsonIgnore
    public TestCaseDto convertTestCase(TestCase testCase) {
        return this.builder().input(testCase.getInput()).output(testCase.getOutput()).marks(testCase.getMarks())
            .isSample(testCase.isSample()).questionId(testCase.getQuestionId()).build();
    }

}

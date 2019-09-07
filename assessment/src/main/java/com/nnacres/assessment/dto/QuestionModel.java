package com.nnacres.assessment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nnacres.assessment.entity.Category;
import com.nnacres.assessment.entity.Option;
import com.nnacres.assessment.entity.Question;
import com.nnacres.assessment.entity.TestCase;
import com.nnacres.assessment.enums.DifficultyLevel;
import com.nnacres.assessment.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.collections.CollectionUtils;

import java.util.HashSet;
import java.util.Set;


/**
 * @author ashwini.pillai
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
public class QuestionModel {
    private Long id;
    private Long questionId;
    private Long customerId;
    private String text;
    private String title;
    private QuestionType type;
    private DifficultyLevel difficultyLevel;
    private Long points;

    private Set<OptionDto> optionsDtos;

    private Set<TestCaseDto> testCaseDtos;

    private Set<CategoryDto> categoryDtos;


    @JsonIgnore
    public QuestionModel convertToQuestion(Question question) {
        Set<TestCaseDto> testCaseDTOset = new HashSet<>();
        Set<OptionDto> optionDtos = new HashSet<>();
        Set<CategoryDto> categoryDtos = new HashSet<>();

        Set<Option> options = question.getOptions();
        if (CollectionUtils.isNotEmpty(options)) {
            for (Option opt : options) {
                OptionDto dto = new OptionDto();
                optionDtos.add(dto.convertOption(opt));
            }
        }

        Set<TestCase> testCases = question.getTestCases();
        if (CollectionUtils.isNotEmpty(testCases)) {
            for (TestCase testCase : testCases) {
                TestCaseDto dto = new TestCaseDto();
                testCaseDTOset.add(dto.convertTestCase(testCase));
            }
        }
        Set<Category> categories = question.getCategory();
        if (CollectionUtils.isNotEmpty(categories)) {
            for (Category category : categories) {
                CategoryDto dto = new CategoryDto();
                categoryDtos.add(dto.convertCategory(category));
            }
        }

        return this.builder().customerId(question.getCustomerId()).text(question.getText())
            .title(question.getTitle()).type(question.getType()).questionId(question.getId())
            .difficultyLevel(question.getDifficultyLevel()).categoryDtos(categoryDtos).optionsDtos(optionDtos)
            .testCaseDtos(testCaseDTOset).points(question.getPoints()).build();


    }
}

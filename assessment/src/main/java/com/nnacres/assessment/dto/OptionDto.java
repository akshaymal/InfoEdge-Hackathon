package com.nnacres.assessment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nnacres.assessment.entity.Option;
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
public class OptionDto {
    private Long questionId;
    private String value;
    private Integer order;
    private Boolean isCorrect;
    private Long optionId;

    @JsonIgnore
    public OptionDto convertOption(Option option) {
        return this.builder().questionId(option.getQuestionId()).value(option.getValue())
            .order(option.getOrder()).isCorrect(option.isCorrect()).build();
    }

    @JsonIgnore
    public OptionDto convertOptionForUser(Option option) {
        return this.builder().questionId(option.getQuestionId()).value(option.getValue()).optionId(option.getId())
            .order(option.getOrder()).build();
    }

}

package com.nnacres.assessment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nnacres.assessment.entity.Contest;
import com.nnacres.assessment.entity.ContestQuestion;
import com.nnacres.assessment.enums.ContestStatus;
import com.nnacres.assessment.enums.ContestType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Vishal on 19/12/17.
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
public class ContestDTO implements Serializable{
    private static final long serialVersionUID = 7250081914567201690L;
    private Long id;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private boolean openContest;
    private ContestStatus status;
    private int duration;
    private ContestType type;
    private boolean isTeamContest;
    private Integer maxTeamSize;
    private Set<ContestQuestionDTO> contestQuestionDTOs;


    @JsonIgnore
    public ContestDTO convertToContestDTO(Contest contest) {
        Set<ContestQuestionDTO> qustionDTOs = new HashSet<>();

        Set<ContestQuestion> contestQuestions = contest.getContestQuestions();
        if (CollectionUtils.isNotEmpty(contestQuestions)) {
            for (ContestQuestion contestQuestion : contestQuestions) {
                ContestQuestionDTO dto = new ContestQuestionDTO();
                qustionDTOs.add(dto.convertContestQuestion(contestQuestion));
            }
        }
        return this.builder().id(contest.getId()).name(contest.getName())
            .description(contest.getDescription()).duration(contest.getDuration())
            .startDate(contest.getStartDate()).endDate(contest.getEndDate())
            .openContest(contest.isOpenContest()).status(contest.getStatus())
            .build();


    }
}


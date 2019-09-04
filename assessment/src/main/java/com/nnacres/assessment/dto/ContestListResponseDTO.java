package com.nnacres.assessment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nnacres.assessment.entity.Contest;
import com.nnacres.assessment.enums.ContestStatus;
import com.nnacres.assessment.enums.ContestType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.io.Serializable;
import java.util.Date;


@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString(doNotUseGetters = true)
public class ContestListResponseDTO implements Serializable {

    private static final long serialVersionUID = -2354333207757565906L;
    private Long id;
    private String name;
	private String description;
	private Date startDate;
	private Date endDate;
	private int participantCount;
	private int questionCount;
	private ContestStatus status;
	private ContestType type;

	public ContestListResponseDTO() {
		super();
	}

	public ContestListResponseDTO(Contest contest) {
		super();
		this.name = contest.getName();
		this.description = contest.getDescription();
		this.startDate = contest.getStartDate();
		this.endDate = contest.getEndDate();
		this.status = contest.getStatus();
		this.type = contest.getType();
		this.id = contest.getId();
		this.questionCount = contest.getContestQuestions().size();
	}
}

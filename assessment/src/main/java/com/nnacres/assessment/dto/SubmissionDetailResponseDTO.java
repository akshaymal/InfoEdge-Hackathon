package com.nnacres.assessment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString(doNotUseGetters = true)
public class SubmissionDetailResponseDTO {
    private double time;
    private int memory;
    private int languageId;
    private Date date;
    private int status;
    private int result;
    private int signal;
    private String source;
    private String input;
    private String outputType;
    private String output;
    private String stderr;
    private String cmpInfo;
    CompilerListDTO compilerListDTOs;

}

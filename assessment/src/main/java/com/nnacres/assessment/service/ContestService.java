package com.nnacres.assessment.service;


import com.nnacres.assessment.dto.ContestDTO;
import com.nnacres.assessment.dto.ContestListResponseDTO;
import com.nnacres.assessment.enums.ContestStatus;

import java.util.List;

/**
 * Created by Vishal on 19/12/17.
 */
public interface ContestService {

    ContestDTO createContest(ContestDTO contestDTO);

    List<ContestListResponseDTO> getContestList();

    ContestDTO getContestDetail(Long id);

    Boolean setStatus(Long id, ContestStatus status);

}

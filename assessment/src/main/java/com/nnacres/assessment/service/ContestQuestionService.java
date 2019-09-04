package com.nnacres.assessment.service;


import com.nnacres.assessment.dto.ContestQuestionDTO;

import java.util.List;
import java.util.Set;

/**
 * Created by Vishal on 19/12/17.
 */
public interface ContestQuestionService {

    List<ContestQuestionDTO> addQuestion(Long id, Set<ContestQuestionDTO> contestQuestionDTOs);
}

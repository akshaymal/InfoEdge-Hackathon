package com.nnacres.assessment.service;


import com.nnacres.assessment.dto.QuestionResponseDTO;

import java.util.List;
import java.util.Set;

/**
 * Created by Neelasha on 20/12/17.
 */
public interface IQuestionResponseService {

    List<QuestionResponseDTO> addQuestionResponse(Long id, Set<QuestionResponseDTO> questionResponseDTOs);
}

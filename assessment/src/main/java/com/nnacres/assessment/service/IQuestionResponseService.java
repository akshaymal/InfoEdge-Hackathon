package com.nnacres.assessment.service;


import com.nnacres.assessment.dto.QuestionResponseDTO;

import java.util.List;
import java.util.Set;


public interface IQuestionResponseService {

    List<QuestionResponseDTO> addQuestionResponse(Long id, Set<QuestionResponseDTO> questionResponseDTOs);
}

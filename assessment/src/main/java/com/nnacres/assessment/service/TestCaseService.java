package com.nnacres.assessment.service;


import com.nnacres.assessment.dto.TestCaseDto;

import java.util.List;
import java.util.Set;

public interface TestCaseService {
    public List<TestCaseDto> addOption(Long questionId, Set<TestCaseDto> testCaseDtos);
}

package com.nnacres.assessment.service;

import com.nnacres.assessment.dto.OptionDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface OptionService {
    public List<OptionDto> addOption(Long questionId, Set<OptionDto> optionDtos);
}

package com.nnacres.assessment.service.impl;

import com.nnacres.assessment.dto.OptionDto;
import com.nnacres.assessment.entity.Option;
import com.nnacres.assessment.repository.OptionRepository;
import com.nnacres.assessment.service.OptionService;
import org.apache.commons.collections.CollectionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class OptionServiceImpl implements OptionService {

    @Autowired
    private OptionRepository optionRepository;

    @Override
    public List<OptionDto> addOption(Long questionId, Set<OptionDto> optionDtos) {
        Timestamp timestamp = Timestamp.from(Instant.now());
        List<Option> options = new ArrayList<>();
        List<OptionDto> optionDtoSet = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(optionDtos)){
            optionDtos.parallelStream().forEach(dto->{
                Option option =
                    Option.builder().questionId(questionId).value(dto.getValue())
                        .order(dto.getOrder()).isCorrect(dto.getIsCorrect()).build();
                option.setCreatedDate(timestamp);
                option.setUpdatedDate(timestamp);

                options.add(option);
            });
            List<Option> optionList = optionRepository.saveAll(options);

            if (CollectionUtils.isNotEmpty(optionList)) {
                for (Option option : optionList) {
                    OptionDto dto = new OptionDto();
                    optionDtoSet.add(dto.convertOption
                        (option));
                }

            }

        }

        return optionDtoSet;
    }
}

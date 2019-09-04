package com.nnacres.assessment.service.impl;

import com.nnacres.assessment.dto.ContestQuestionDTO;
import com.nnacres.assessment.entity.ContestQuestion;
import com.nnacres.assessment.exception.GenericException;
import com.nnacres.assessment.repository.ContestQuestionRepository;
import com.nnacres.assessment.service.ContestQuestionService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Vishal on 19/12/17.
 */

@Service
public class ContestQuestionServiceImpl implements ContestQuestionService {

    @Autowired
    private ContestQuestionRepository contestQuestionRepository;

    @Override
    @Transactional(rollbackFor = GenericException.class)
    public List<ContestQuestionDTO> addQuestion(final Long contestId,
                                                Set<ContestQuestionDTO> dtos) {
        List<ContestQuestion> contestQuestions = new ArrayList<>();
        List<ContestQuestionDTO> responseDtos = new ArrayList<>();
        Timestamp timestamp = Timestamp.from(Instant.now());
        if (CollectionUtils.isNotEmpty(dtos)) {
            dtos.parallelStream().forEach(dto->{
                ContestQuestion contestQuestion =
                    ContestQuestion.builder().contestId(contestId).questionId(dto.getQuestionId())
                        .questionTitle(dto.getQuestionTitle()).points(dto.getPoints())
                        .negativePoints(dto.getNegativePoints()).build();

                contestQuestion.setCreatedDate(timestamp);
                contestQuestion.setUpdatedDate(timestamp);

                contestQuestions.add(contestQuestion);
            });


            List<ContestQuestion> responses = contestQuestionRepository.saveAll(contestQuestions);
            if (CollectionUtils.isNotEmpty(responses)) {
                for (ContestQuestion contestQuestion : responses) {
                    ContestQuestionDTO dto = new ContestQuestionDTO();
                    responseDtos.add(dto.convertContestQuestion(contestQuestion));
                }

            }
        }
        return responseDtos;
    }
}

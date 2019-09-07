package com.nnacres.assessment.service.impl;

import com.nnacres.assessment.dto.ContestDTO;
import com.nnacres.assessment.dto.ContestListResponseDTO;
import com.nnacres.assessment.entity.Contest;
import com.nnacres.assessment.enums.ContestStatus;
import com.nnacres.assessment.exception.GenericException;
import com.nnacres.assessment.helper.ContestHelper;
import com.nnacres.assessment.repository.ContestQuestionRepository;
import com.nnacres.assessment.repository.ContestRepository;
import com.nnacres.assessment.repository.QuestionRepository;
import com.nnacres.assessment.service.ContestService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ContestServiceImpl implements ContestService {


    @Autowired
    private ContestRepository contestRepository;
    @Autowired
    private ContestQuestionRepository contestQuestionRepository;
    @Autowired
    private QuestionRepository questionRepository;


    @Override
    @Transactional(rollbackFor = GenericException.class)
    public ContestDTO createContest(ContestDTO contestDTO) {
        Contest contest =
            Contest.builder().name(contestDTO.getName()).openContest(contestDTO.isOpenContest())
                .description(contestDTO.getDescription()).duration(contestDTO.getDuration())
                .customerId(123L).startDate(contestDTO.getStartDate()).type(contestDTO.getType())
                .endDate(contestDTO.getEndDate()).status(ContestStatus.DRAFT).build();



        contest = contestRepository.save(contest);
        return contestDTO.convertToContestDTO(contest);

    }

    @Override
    public List<ContestListResponseDTO> getContestList() {
        List<Contest> contests = contestRepository.findAll();
        if(CollectionUtils.isEmpty(contests)){
            new ArrayList<>();
        }
        return ContestHelper.createContestDetailsByUser(contests);

    }

    @Override
    public ContestDTO getContestDetail(Long id) {
        Optional<Contest> contest = contestRepository.findById(id);
        ContestDTO contestDTO = new ContestDTO();
        if(contest == null || !contest.isPresent()) {
            return contestDTO;
        }
        return contestDTO.convertToContestDTO(contest.get());
    }

    @Override
    @Transactional(rollbackFor = GenericException.class)
    public Boolean setStatus(Long id, ContestStatus status) {
        Optional<Contest> contest = contestRepository.findById(id);
        if(contest == null){
            return Boolean.FALSE;
        }
        contest.get().setStatus(status);
        return Boolean.TRUE;
    }
}

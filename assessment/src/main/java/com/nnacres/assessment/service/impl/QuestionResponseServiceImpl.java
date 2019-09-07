package com.nnacres.assessment.service.impl;

import com.SphereEngine.Api.Exception.ClientException;
import com.SphereEngine.Api.Exception.ConnectionException;
import com.nnacres.assessment.dto.CodeResponseDTO;
import com.nnacres.assessment.dto.QuestionResponseDTO;
import com.nnacres.assessment.entity.Option;
import com.nnacres.assessment.entity.Question;
import com.nnacres.assessment.entity.QuestionResponse;
import com.nnacres.assessment.enums.QuestionType;
import com.nnacres.assessment.exception.GenericException;
import com.nnacres.assessment.repository.QuestionRepository;
import com.nnacres.assessment.repository.QuestionResponseRepository;
import com.nnacres.assessment.response.ResponseObject;
import com.nnacres.assessment.service.IQuestionResponseService;
import com.nnacres.assessment.service.SphereEngineCompilerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@Slf4j
public class QuestionResponseServiceImpl implements IQuestionResponseService {

    @Autowired
    private QuestionResponseRepository questionResponseRepository;

    @Autowired
    private SphereEngineCompilerService sphereEngineCompilerService;

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    @Transactional(rollbackFor = GenericException.class)
    public List<QuestionResponseDTO> addQuestionResponse(final Long id, Set<QuestionResponseDTO> dtos) {
        List<QuestionResponse> questionResponses = new ArrayList<>();
        List<QuestionResponseDTO> responseDtos = new ArrayList<>();
        Timestamp timestamp = Timestamp.from(Instant.now());
        if (CollectionUtils.isNotEmpty(dtos)) {
            dtos.parallelStream().forEach(dto->{
                QuestionResponse questionResponse =
                    QuestionResponse.builder().contestId(dto.getContestId())
                        .questionId(dto.getQuestionId()).language(dto.getLanguageId())
                        .userId(dto.getUserId()).timeTaken(dto.getTimeTaken()).program(dto.getProgram())
                        .answerGiven(String.valueOf(dto.getAnswerGiven())).build();


                if(QuestionType.CODING.equals(dto.getQuestionType())){
                    CodeResponseDTO codeResponseDTO =
                        CodeResponseDTO.builder().input("").languageId(dto.getLanguageId())
                            .questionId(dto.getQuestionId()).source(dto.getProgram()).build();
                    ResponseObject<List<Boolean>> responseObject = new ResponseObject<>();
                    try {
                        if (!sphereEngineCompilerService
                            .checkCompilationFailed(codeResponseDTO, responseObject)) {
                            List<Integer> marks = new ArrayList<>();
                            sphereEngineCompilerService
                                .executeTestCases(codeResponseDTO, new ArrayList<>(), marks);
                            log.error("marks : ", marks);
                            questionResponse.setMarks(marks.stream().mapToInt(i->i).sum());

                        }
                    } catch (ClientException e) {
                        e.printStackTrace();
                    } catch (ConnectionException e) {
                        e.printStackTrace();
                    }

                }

                else {
                    Optional<Question> question = questionRepository.findById(dto.getQuestionId());
                    Set<Option> options = question.get().getOptions();
                    log.error("option selected : ", dto.getAnswerGiven());
                    boolean isCorrect = true;
                    for(Option option : options){
                        if(option.isCorrect() && !dto.getAnswerGiven().contains(option.getId())){
                            log.error("option : ", option);
                            isCorrect = false;
                            break;
                        }
                    }
                    if(isCorrect){
                        log.error("called isCorrect true");
                        questionResponse.setMarks(dto.getMarks());
                    }
                    else {
                        log.error("called isCorrect false");
                        questionResponse.setMarks(dto.getNegativeMarks());
                    }

                }
            	questionResponse.setCreatedDate(timestamp);
                questionResponse.setUpdatedDate(timestamp);
                questionResponses.add(questionResponse);

            });


            List<QuestionResponse> responses = questionResponseRepository.saveAll(questionResponses);
            if (CollectionUtils.isNotEmpty(responses)) {
                for (QuestionResponse questionResponse : responses) {
                    QuestionResponseDTO dto = new QuestionResponseDTO();
                    responseDtos.add(dto.convertQuestionResponse(questionResponse));
                }

            }
        }
        return responseDtos;
    }
}

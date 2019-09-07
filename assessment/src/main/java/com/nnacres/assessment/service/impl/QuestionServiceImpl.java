package com.nnacres.assessment.service.impl;

import com.nnacres.assessment.dto.CategoryDto;
import com.nnacres.assessment.dto.OptionDto;
import com.nnacres.assessment.dto.QuestionCriteriaDTO;
import com.nnacres.assessment.dto.QuestionModel;
import com.nnacres.assessment.dto.RandomQuestionDTO;
import com.nnacres.assessment.dto.TestCaseDto;
import com.nnacres.assessment.entity.Option;
import com.nnacres.assessment.entity.Question;
import com.nnacres.assessment.entity.TestCase;
import com.nnacres.assessment.exception.GenericException;
import com.nnacres.assessment.repository.OptionRepository;
import com.nnacres.assessment.repository.QuestionRepository;
import com.nnacres.assessment.service.CategoryService;
import com.nnacres.assessment.service.IQuestionService;
import com.nnacres.assessment.service.OptionService;
import com.nnacres.assessment.service.TestCaseService;
import org.apache.commons.collections.CollectionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


/**
 * @author ashwini.pillai
 */

@Service
public class QuestionServiceImpl implements IQuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private OptionService optionService;
    @Autowired
    private TestCaseService testCaseService;
    @Autowired
    private CategoryService categoryService;


    @Transactional
    @Override
    public QuestionModel saveQuestion(QuestionModel question) {
        Question questionEntity =
            Question.builder().customerId(question.getCustomerId()).text(question.getText())
                .title(question.getTitle()).type(question.getType())
                .difficultyLevel(question.getDifficultyLevel()).build();

        Timestamp timestamp = Timestamp.from(Instant.now());

        questionEntity.setCustomerId(question.getCustomerId());
        questionEntity.setCreatedDate(timestamp);
        questionEntity.setUpdatedDate(timestamp);

        questionEntity = questionRepository.save(questionEntity);
        optionService.addOption(questionEntity.getId(), question.getOptionsDtos());
        testCaseService.addOption(questionEntity.getId(), question.getTestCaseDtos());
        List<CategoryDto> categoryDtosSet = categoryService.addOption(question.getCategoryDtos());
        questionEntity.setCategoryId(categoryDtosSet.get(0).getId());
        return question.convertToQuestion(questionEntity);
    }

    @Override
    public List<QuestionModel> getQuestion() {
        List<Question> question = questionRepository.find(new PageRequest(0, 10, Sort.Direction.DESC, "id"));
        List<QuestionModel> questionModel = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(question)) {
            question.parallelStream().forEach(dto -> {
                QuestionModel questionModel1 = QuestionModel.builder().customerId(dto.getCustomerId()).text(dto.getText())
                    .title(dto.getTitle()).type(dto.getType())
                    .questionId(dto.getId())
                    .difficultyLevel(dto.getDifficultyLevel()).build();

                questionModel.add(questionModel1);
            });

        }
        return questionModel;

    }

    @Override
    @Transactional(rollbackOn = GenericException.class)
    public List<QuestionModel> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        List<QuestionModel> questionModels = new ArrayList<>();
        questions.forEach(question -> {
            Set<OptionDto> optionDtoSet = new HashSet<>();
            Set<TestCase> testCases = question.getTestCases();
            Set<Option> options = question.getOptions();
            Set<TestCaseDto> testCaseDtoSet = new HashSet<>();
            if (CollectionUtils.isNotEmpty(testCases)) {
                for (TestCase testCase : testCases) {
                    if (testCase.isSample()) {
                        TestCaseDto dto = new TestCaseDto();
                        testCaseDtoSet.add(dto.convertTestCase(testCase));
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(options)) {
                for (Option option : options) {
                    {
                        OptionDto dto = new OptionDto();
                        optionDtoSet.add(dto.convertOptionForUser(option));
                    }
                }
            }
            QuestionModel questionModel = QuestionModel.builder().customerId(question.getCustomerId()).text(question.getText())
                .title(question.getTitle()).type(question.getType()).optionsDtos(optionDtoSet).questionId((question.getId()))
                .testCaseDtos(testCaseDtoSet).difficultyLevel(question.getDifficultyLevel()).build();
            questionModels.add(questionModel);
        });
        return questionModels;
    }


    @Override
    @Transactional(rollbackOn = GenericException.class)
    public QuestionModel getQuestionById(Long id) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        Question question = optionalQuestion.get();

        Set<TestCaseDto> testCaseDtoSet = new HashSet<>();

        Set<OptionDto> optionDtos = new HashSet<>();
        Set<Option> options = question.getOptions();

        if(CollectionUtils.isNotEmpty(options)) {
            options.stream().forEach(option -> {
                OptionDto dto = new OptionDto();
                optionDtos.add(dto.convertOption(option));
            });
        }

        Set<TestCase> testCases = question.getTestCases();
        if (CollectionUtils.isNotEmpty(testCases)) {
            for (TestCase testCase : testCases) {
                if (testCase.isSample()) {
                    TestCaseDto dto = new TestCaseDto();
                    testCaseDtoSet.add(dto.convertTestCase(testCase));
                }
            }

        }

        return QuestionModel.builder().customerId(question.getCustomerId()).text(question.getText())
            .title(question.getTitle()).type(question.getType()).optionsDtos(optionDtos)
            .testCaseDtos(testCaseDtoSet).difficultyLevel(question.getDifficultyLevel()).build();
    }

    @Override
    public List<List<QuestionModel>> getFixedQuestion(RandomQuestionDTO randomQuestionDTO) {
        int totalSize = randomQuestionDTO.getTotalQuestions();
        PageRequest pageRequest = new PageRequest(0, 10, Sort.Direction.DESC, "id");
        Set<QuestionCriteriaDTO> questionCriteriaDTOSet = randomQuestionDTO.getQuestionCriteriaDTOs();

        List<List<QuestionModel>> totalQuestionList = new ArrayList<>();
        questionCriteriaDTOSet.forEach(dto -> {
            List<Question> question = questionRepository.findByCategoryNameAndDifficultyLevelAndType(dto.getCategoryName(),
                dto.getDifficultyLevel(), dto.getType(), (new PageRequest(0, dto.getQuestionCount())));
            List<QuestionModel> questionModel = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(question)) {
                question.parallelStream().forEach(dto1 -> {
                    QuestionModel questionModel1 = QuestionModel.builder().customerId(dto1.getCustomerId()).text(dto1.getText())
                        .title(dto1.getTitle()).type(dto1.getType()).id(dto1.getId())
                        .difficultyLevel(dto.getDifficultyLevel()).build();

                    questionModel.add(questionModel1);
                });
            }
            totalQuestionList.add(questionModel);
        });
        return totalQuestionList;

    }

    @Override
    public QuestionModel findOne(Long id) {
        Optional<Question> questionOptional = questionRepository.findById(id);

        /*Set<Option> options = question.getOptions();
        Set<OptionDto> optionDtos = new HashSet<>();*/
        Set<TestCaseDto> testCaseDtoSet = new HashSet<>();

        /*if (CollectionUtils.isNotEmpty(options)) {
            for (Option option : options) {
                OptionDto dto = new OptionDto();
                optionDtos.add(dto.convertOption(option));
            }

        }*/

        Question question = questionOptional.get();
        Set<TestCase> testCases = question.getTestCases();
        if (CollectionUtils.isNotEmpty(testCases)) {
            for (TestCase testCase : testCases) {
                TestCaseDto dto = new TestCaseDto();
                testCaseDtoSet.add(dto.convertTestCase(testCase));
            }

        }
        return QuestionModel.builder().customerId(question.getCustomerId()).text(question.getText())
            .title(question.getTitle()).type(question.getType())/*.optionsDtos(optionDtos)*/
            .testCaseDtos(testCaseDtoSet).difficultyLevel(question.getDifficultyLevel()).build();
    }


}

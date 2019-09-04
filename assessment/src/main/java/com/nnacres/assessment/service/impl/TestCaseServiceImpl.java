package com.nnacres.assessment.service.impl;

import com.nnacres.assessment.dto.TestCaseDto;
import com.nnacres.assessment.entity.TestCase;
import com.nnacres.assessment.repository.TestCasesRepository;
import com.nnacres.assessment.service.TestCaseService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class TestCaseServiceImpl implements TestCaseService {

    @Autowired
    private TestCasesRepository testCasesRepository;

    @Override
    public List<TestCaseDto> addOption(Long questionId, Set<TestCaseDto> testCaseDtos) {
        Timestamp timestamp = Timestamp.from(Instant.now());
        List<TestCase> testCases = new ArrayList<>();
        List<TestCaseDto> testCaseDtoSet = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(testCaseDtos)){
            testCaseDtos.parallelStream().forEach(dto->{

                TestCase testCase =
                    TestCase.builder().questionId(questionId).input(dto.getInput())
                        .output(dto.getOutput()).isSample(dto.isSample()).build();

                testCase.setCreatedDate(timestamp);
                testCase.setUpdatedDate(timestamp);
                testCases.add(testCase);
            });
            List<TestCase> responseTestCases = testCasesRepository.saveAll(testCases);

            if (CollectionUtils.isNotEmpty(responseTestCases)) {
                for (TestCase testCase : responseTestCases) {
                    TestCaseDto dto = new TestCaseDto();
                    testCaseDtoSet.add(dto.convertTestCase(testCase));
                }

            }

        }
        return testCaseDtoSet;
    }
}

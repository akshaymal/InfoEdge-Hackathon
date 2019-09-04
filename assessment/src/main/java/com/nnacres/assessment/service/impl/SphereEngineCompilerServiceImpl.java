package com.nnacres.assessment.service.impl;

import com.SphereEngine.Api.CompilersClientV3;
import com.SphereEngine.Api.Exception.ClientException;
import com.SphereEngine.Api.Exception.ConnectionException;
import com.SphereEngine.Api.Exception.NotAuthorizedException;
import com.SphereEngine.Api.Exception.NotFoundException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.nnacres.assessment.dto.CodeResponseDTO;
import com.nnacres.assessment.dto.CompilerListDTO;
import com.nnacres.assessment.dto.QuestionModel;
import com.nnacres.assessment.dto.SubmissionDetailResponseDTO;
import com.nnacres.assessment.dto.TestCaseDto;
import com.nnacres.assessment.exception.GenericException;
import com.nnacres.assessment.response.ResponseObject;
import com.nnacres.assessment.service.IQuestionService;
import com.nnacres.assessment.service.SphereEngineCompilerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.nnacres.assessment.enums.ErrorCategory.SPHERE_ENGINE_COMPILATION_FAILED;


/**
 * Created by Vishal on 20/12/17.
 */

@Service
@Slf4j
public class SphereEngineCompilerServiceImpl implements SphereEngineCompilerService {


    @Autowired
    private IQuestionService questionService;

    private CompilersClientV3 client;

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @PostConstruct
    public void setCompilersClientV3() {
        try {
            client = new CompilersClientV3("d13037ba863a340000c6e40a59a7c98b",
                "43731bfd.compilers.sphere-engine.com");
            client.test();
        } catch (NotAuthorizedException e) {
            log.error("Invalid access token");
        } catch (ConnectionException e) {
            log.error("API connection problem");
        } catch (ClientException e) {
            log.error("Client Exception problem");
        }
    }

    @Override
    public SubmissionDetailResponseDTO getSubmission(Integer id, Boolean withSource,
                                                     Boolean withInput, Boolean withOutput, Boolean withStderr, Boolean withCmpInfo) {
        SubmissionDetailResponseDTO detailResponseDTO = null;

        try {
            JsonObject response = client
                .getSubmission(id, withSource, withInput, withOutput, withStderr, withCmpInfo);
            if (response != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                detailResponseDTO = getSubmissionDetailResponseDTO(response);
                JsonObject jsonObject = response.get("compiler").getAsJsonObject();
                CompilerListDTO dto = CompilerListDTO.builder().id(jsonObject.get("id").getAsInt())
                    .ver(jsonObject.get("ver").getAsString())
                    .name(jsonObject.get("name").toString()).build();

                detailResponseDTO.setCompilerListDTOs(dto);
            }

        } catch (NotAuthorizedException e) {
            log.error("Invalid access token");
        } catch (NotFoundException e) {
            log.error("Submission does not exist");
        } catch (ClientException e) {
            log.error(e.getMessage());
        } catch (ConnectionException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detailResponseDTO;
    }

    @Override
    public String getSubmissionStream(Integer id, String stream) throws GenericException {
        String response = null;
        try {
            response = client.getSubmissionStream(id, stream);
        } catch (NotAuthorizedException e) {
            log.error("Invalid access token");
        } catch (NotFoundException e) {
            // aggregates two possible reasons of 404 error
            // non existing submission or stream
            log.error(
                "Non existing resource (submission, stream), details available in the message: " + e
                    .getMessage());
        } catch (ClientException e) {
            log.error(e.getMessage());
        } catch (ConnectionException e) {
            log.error(e.getMessage());
        }
        return response;
    }

    @Override
    public JsonObject getSubmissions(Integer[] ids) throws GenericException {
        JsonObject response = null;

        try {
            response = client.getSubmissions(ids);
        } catch (NotAuthorizedException e) {
            log.error("Invalid access token");
        } catch (NotFoundException e) {
            log.error("Submission does not exist");
        } catch (ClientException e) {
            log.error(e.getMessage());
        } catch (ConnectionException e) {
            log.error(e.getMessage());
        }
        return response;
    }

    @Override
    public List<CompilerListDTO> getCompilers() {
        List<CompilerListDTO> dtos = new ArrayList<>();

        try {
            JsonObject response = client.getCompilers();
            if (response != null) {
                JsonArray jsonArray = response.get("items").getAsJsonArray();
                int size = jsonArray.size();
                for (int i = 0; i < size; i++) {
                    JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                    CompilerListDTO dto = CompilerListDTO.builder()
                        .id(Integer.valueOf(String.valueOf(jsonObject.get("id")).replace("\"", "")))
                        .name(jsonObject.get("name").getAsString())
                        .ver(jsonObject.get("ver").getAsString()).build();
                    dtos.add(dto);
                }
            }
        } catch (NotAuthorizedException e) {
            log.error("Invalid access token");
        } catch (ClientException e) {
            log.error(e.getMessage());
        } catch (ConnectionException e) {
            log.error(e.getMessage());
        }
        return dtos;
    }

    @Override
    public Integer createSubmission(CodeResponseDTO codeResponseDTO) {
        Integer id = null;
        try {
            JsonObject response = client
                .createSubmission(codeResponseDTO.getSource(), codeResponseDTO.getLanguageId(),
                    codeResponseDTO.getInput());
            if (response != null) {
                id = response.get("id").getAsInt();
            }
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (ConnectionException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public ResponseObject<List<Boolean>> getResult(CodeResponseDTO codeResponseDTO) {

        ResponseObject<List<Boolean>> responseObject = new ResponseObject<>();
        List<Boolean> responses = new ArrayList<>();
        try {
            if (checkCompilationFailed(codeResponseDTO, responseObject)) {
                return responseObject;
            }
            executeTestCases(codeResponseDTO, responses, new ArrayList<>());
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (ConnectionException e) {
            e.printStackTrace();
        }
        responseObject.setResponseObject(responses);
        return responseObject;
    }

    @Override
    public boolean checkCompilationFailed(CodeResponseDTO codeResponseDTO,
        ResponseObject<List<Boolean>> responseObject) throws ClientException, ConnectionException {
        Integer id = createSubmission(codeResponseDTO);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JsonObject response = client.getSubmission(id, true, true, true, true, true);
        if (!response.get("cmpinfo").getAsString().isEmpty()) {
            responseObject.setStatus(SPHERE_ENGINE_COMPILATION_FAILED);
            responseObject.setErrorMessage(response.get("cmpinfo").getAsString());
            return true;
        }
        return false;
    }

    @Override
    public void executeTestCases(CodeResponseDTO codeResponseDTO, List<Boolean> responses,
        List<Integer> marks)
        throws ClientException, ConnectionException {
        QuestionModel model = questionService.findOne(codeResponseDTO.getQuestionId());
        Set<TestCaseDto> testCaseDtos = model.getTestCaseDtos();
        if (CollectionUtils.isNotEmpty(testCaseDtos)) {
            for (TestCaseDto dto : testCaseDtos) {
                codeResponseDTO.setInput(dto.getInput());
                final Integer responseId = createSubmission(codeResponseDTO);
                try {
                    Thread.sleep(10000);
                    JsonObject jsonObject =
                        client.getSubmission(responseId, true, true, true, true, true);
                    SubmissionDetailResponseDTO submissionDetailResponseDTO =
                        getSubmissionDetailResponseDTO(jsonObject);
                        if (submissionDetailResponseDTO.getOutput().trim().equals(dto.getOutput
                            ().trim())) {
                            responses.add(Boolean.TRUE);
                            marks.add(dto.getMarks());
                        } else {
                            responses.add(Boolean.FALSE);
                            marks.add(0);
                        }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private SubmissionDetailResponseDTO getSubmissionDetailResponseDTO(JsonObject jsonObject) {
        return SubmissionDetailResponseDTO.builder()
            .time(jsonObject.get("time").getAsDouble())
            .memory(jsonObject.get("memory").getAsInt())
            .result(jsonObject.get("result").getAsInt())
            .status(jsonObject.get("status").getAsInt())
            .signal(jsonObject.get("signal").getAsInt())
            .languageId(jsonObject.get("langId").getAsInt())
            .source(jsonObject.get("source").getAsString())
            .output(jsonObject.get("output").getAsString())
            .input(jsonObject.get("input").getAsString())
            .cmpInfo(jsonObject.get("cmpinfo").getAsString())
            .outputType(jsonObject.get("output_type").getAsString())
            .stderr(jsonObject.get("stderr").getAsString()).build();
    }
}

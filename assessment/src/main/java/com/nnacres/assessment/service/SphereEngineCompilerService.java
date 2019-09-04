package com.nnacres.assessment.service;

import com.SphereEngine.Api.Exception.ClientException;
import com.SphereEngine.Api.Exception.ConnectionException;
import com.google.gson.JsonObject;
import com.nnacres.assessment.dto.CodeResponseDTO;
import com.nnacres.assessment.dto.CompilerListDTO;
import com.nnacres.assessment.dto.SubmissionDetailResponseDTO;
import com.nnacres.assessment.exception.GenericException;
import com.nnacres.assessment.response.ResponseObject;

import java.util.List;


/**
 * Created by Vishal on 20/12/17.
 */
public interface SphereEngineCompilerService {

    SubmissionDetailResponseDTO getSubmission(Integer id, Boolean withSource, Boolean withInput,
                                              Boolean withOutput, Boolean withStderr, Boolean withCmpInfo);

    String getSubmissionStream(Integer id, String stream) throws GenericException;

    JsonObject getSubmissions(Integer[] ids) throws GenericException;

    List<CompilerListDTO> getCompilers();

    Integer createSubmission(CodeResponseDTO codeResponseDTO);

    ResponseObject<List<Boolean>> getResult(CodeResponseDTO codeResponseDTO);

    boolean checkCompilationFailed(CodeResponseDTO codeResponseDTO,
                                   ResponseObject<List<Boolean>> responseObject) throws ClientException, ConnectionException;

    void executeTestCases(CodeResponseDTO codeResponseDTO, List<Boolean> responses,
                          List<Integer> marks)
        throws ClientException, ConnectionException;
}

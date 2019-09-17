package com.nnacres.assessment.service.impl;

import java.util.concurrent.Callable;

import com.SphereEngine.Api.CompilersClientV3;
import com.google.gson.JsonObject;
import com.nnacres.assessment.dto.ExecutionTaskResult;
import com.nnacres.assessment.dto.SubmissionDetailResponseDTO;
import com.nnacres.assessment.dto.TestCaseDto;

public class EvaluationTask implements Callable<ExecutionTaskResult> {
   private Integer responseId;
   private CompilersClientV3 client;
   private TestCaseDto dto;
   public EvaluationTask(Integer responseId, CompilersClientV3 client, TestCaseDto dto) {
       this.responseId = responseId;
       this.client = client;
       this.dto = dto;
   }
   @Override
   public ExecutionTaskResult call() throws Exception {
       try {
           Thread.sleep(10000);
           JsonObject jsonObject = client.getSubmission(responseId, true, true, true, true, true);
           SubmissionDetailResponseDTO submissionDetailResponseDTO =
               getSubmissionDetailResponseDTO(jsonObject);
           if (submissionDetailResponseDTO.getOutput().trim().equals(dto.getOutput().trim())) {
               //                responses.add(Boolean.TRUE);
               //                marks.add(dto.getMarks());
               return new ExecutionTaskResult(Boolean.TRUE, dto.getMarks());
           } else {
               //                responses.add(Boolean.FALSE);
               //                marks.add(0);
               return new ExecutionTaskResult(Boolean.FALSE, 0);
           }
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
       return null;
   }
   private SubmissionDetailResponseDTO getSubmissionDetailResponseDTO(JsonObject jsonObject) {
       return SubmissionDetailResponseDTO.builder().time(jsonObject.get("time").getAsDouble())
           .memory(jsonObject.get("memory").getAsInt()).result(jsonObject.get("result").getAsInt())
           .status(jsonObject.get("status").getAsInt()).signal(jsonObject.get("signal").getAsInt())
           .languageId(jsonObject.get("langId").getAsInt())
           .source(jsonObject.get("source").getAsString())
           .output(jsonObject.get("output").getAsString())
           .input(jsonObject.get("input").getAsString())
           .cmpInfo(jsonObject.get("cmpinfo").getAsString())
           .outputType(jsonObject.get("output_type").getAsString())
           .stderr(jsonObject.get("stderr").getAsString()).build();
   }
}
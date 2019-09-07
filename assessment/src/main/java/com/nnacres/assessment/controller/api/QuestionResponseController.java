package com.nnacres.assessment.controller.api;


import com.nnacres.assessment.dto.QuestionResponseDTO;
import com.nnacres.assessment.response.ResponseObject;
import com.nnacres.assessment.service.IQuestionResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("${api.version}/questionresponse")
public class QuestionResponseController {

    @Autowired
    private IQuestionResponseService questionResponseService;
 @PostMapping(value = "/{id}/save-response")
    public ResponseEntity<ResponseObject<List<QuestionResponseDTO>>> saveResponse(
         @PathVariable Long id, @RequestBody Set<QuestionResponseDTO> questionResponseDTOs) {
        List<QuestionResponseDTO> dtos = questionResponseService.addQuestionResponse(id, questionResponseDTOs);
        ResponseObject<List<QuestionResponseDTO>> responseObject = new ResponseObject<>(dtos);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }



}

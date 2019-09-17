package com.nnacres.assessment.controller.api;


import com.nnacres.assessment.dto.CodeResponseDTO;
import com.nnacres.assessment.dto.CompilerListDTO;
import com.nnacres.assessment.dto.SubmissionDetailResponseDTO;
import com.nnacres.assessment.response.ResponseObject;
import com.nnacres.assessment.service.SphereEngineCompilerService;
import com.nnacres.assessment.service.SphereEngineProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("${api.version}/engine")
public class SphereEngineController {


    @Autowired
    private SphereEngineCompilerService sphereEngineCompilerService;

    @Autowired
    private SphereEngineProblemService sphereEngineProblemService;


    @GetMapping(value = "/get-compilers")
    public ResponseEntity<ResponseObject<List<CompilerListDTO>>> getCompilers() {

        List<CompilerListDTO> dtos = sphereEngineCompilerService.getCompilers();
        ResponseObject<List<CompilerListDTO>> responseObject = new ResponseObject<>(dtos);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }


    @GetMapping(value = "/get-submission/{id}")
    public ResponseEntity<ResponseObject<SubmissionDetailResponseDTO>> getSubmission(
            @PathVariable Integer id, @RequestParam Boolean withSource, @RequestParam Boolean withInput,
            @RequestParam Boolean withOutput, @RequestParam Boolean withStderr,
            @RequestParam Boolean withCmpInfo) {

        SubmissionDetailResponseDTO dto = sphereEngineCompilerService
            .getSubmission(id, withSource, withInput, withOutput, withStderr, withCmpInfo);
        ResponseObject<SubmissionDetailResponseDTO> responseObject = new ResponseObject<>(dto);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }


    @PostMapping(value = "/submission/create")
    public ResponseEntity<ResponseObject<Integer>> createSubmission(@RequestBody CodeResponseDTO codeResponseDTO) {

        Integer status = sphereEngineCompilerService.createSubmission(codeResponseDTO);
        ResponseObject<Integer> responseObject = new ResponseObject<>(status);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @PostMapping(value = "/get-result")
    public ResponseEntity<ResponseObject<List<Boolean>>> getResult(@RequestBody CodeResponseDTO codeResponseDTO) {
        ResponseObject<List<Boolean>> responseObject;
         responseObject = sphereEngineCompilerService.getResult(codeResponseDTO);
         return new ResponseEntity<ResponseObject<List<Boolean>>>(responseObject, HttpStatus.OK);
    }


}

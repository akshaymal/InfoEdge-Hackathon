package com.nnacres.assessment.controller.api;


import com.nnacres.assessment.dto.ContestDTO;
import com.nnacres.assessment.dto.ContestListResponseDTO;
import com.nnacres.assessment.dto.ContestQuestionDTO;
import com.nnacres.assessment.enums.ContestStatus;
import com.nnacres.assessment.response.ResponseObject;
import com.nnacres.assessment.service.ContestQuestionService;
import com.nnacres.assessment.service.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("${api.version}/contest")
public class ContestController {

    @Autowired
    private ContestService contestService;

    @Autowired
    private ContestQuestionService contestQuestionService;


    @PostMapping(value = "/create")
    public ResponseEntity<ResponseObject<ContestDTO>> createContest(
        @RequestBody ContestDTO contestDTO) {
        ContestDTO dto = contestService.createContest(contestDTO);
        ResponseObject<ContestDTO> responseObject = new ResponseObject<ContestDTO>(dto);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/add-questions")
    public ResponseEntity<ResponseObject<List<ContestQuestionDTO>>> addQuestions(
            @PathVariable Long id, @RequestBody Set<ContestQuestionDTO> contestQuestionDTOs) {
        List<ContestQuestionDTO> dtos = contestQuestionService.addQuestion(id, contestQuestionDTOs);
        ResponseObject<List<ContestQuestionDTO>> responseObject = new ResponseObject<>(dtos);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }



    @GetMapping(value = "/view-contest-list")
    public ResponseEntity<ResponseObject<List<ContestListResponseDTO>>> getContestList() {
        List<ContestListResponseDTO> contestDetailsByUserList = contestService.getContestList();
        ResponseObject<List<ContestListResponseDTO>> responseObject =
            new ResponseObject<>(contestDetailsByUserList);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseObject<ContestDTO>> getContestDetail(@PathVariable Long id) {
        ContestDTO contestDetail = contestService.getContestDetail(id);
        ResponseObject<ContestDTO> responseObject = new ResponseObject<>(contestDetail);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/status/active")
    public ResponseEntity<ResponseObject<Boolean>> setStatusActive(@PathVariable Long id) {
        Boolean status = contestService.setStatus(id, ContestStatus.ACTIVE);
        ResponseObject<Boolean> responseObject = new ResponseObject<>(status);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }


}

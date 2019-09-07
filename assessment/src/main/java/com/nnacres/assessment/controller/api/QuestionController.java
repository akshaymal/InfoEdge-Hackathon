package com.nnacres.assessment.controller.api;

import com.nnacres.assessment.dto.CategoryDto;
import com.nnacres.assessment.dto.QuestionModel;
import com.nnacres.assessment.dto.RandomQuestionDTO;
import com.nnacres.assessment.response.ResponseObject;
import com.nnacres.assessment.service.CategoryService;
import com.nnacres.assessment.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("${api.version}/question")
public class QuestionController {
    @Autowired
    private IQuestionService questionService;
    @Autowired
    private CategoryService categoryService;

    @PostMapping(value = "/save")
    public ResponseEntity saveQuestion(@RequestBody QuestionModel question) {
        QuestionModel saveQuestion = questionService.saveQuestion(question);
        ResponseObject<QuestionModel> responseObject = new ResponseObject<QuestionModel>(question);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @RequestMapping(value = "/getQuestions", method = RequestMethod.GET)
    public ResponseEntity<ResponseObject<List<QuestionModel>>> getQuestion() {
        List<QuestionModel> question = questionService.getQuestion();
        ResponseObject<List<QuestionModel>> responseObject = new ResponseObject<>(question);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }
    @RequestMapping(value = "/getAllQuestions", method = RequestMethod.GET)
    public ResponseEntity<ResponseObject<List<QuestionModel>>> getAllQuestions() {
        List<QuestionModel> question = questionService.getAllQuestions();
        ResponseObject<List<QuestionModel>> responseObject = new ResponseObject<>(question);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }
    @RequestMapping(value = "/getQuestion/{id}", method = RequestMethod.GET)
    public ResponseEntity<ResponseObject<QuestionModel>> getQuestionById(@PathVariable Long id) {
        QuestionModel question = questionService.getQuestionById(id);
        ResponseObject<QuestionModel> responseObject = new ResponseObject<>(question);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @RequestMapping(value = "/getFixedQuestions", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseObject<List<List<QuestionModel>>>> getFixedQuestions(@RequestBody RandomQuestionDTO randomQuestionDTO) {
        List<List<QuestionModel>> question = questionService.getFixedQuestion(randomQuestionDTO);
        ResponseObject<List<List<QuestionModel>>> responseObject = new ResponseObject<>(question);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }
    @RequestMapping(value = "/getCategories",method = RequestMethod.GET)
    public ResponseEntity<ResponseObject<List<CategoryDto>>> getCategories() {
        List<CategoryDto> categoryDetail =categoryService.getCategories();
        ResponseObject<List<CategoryDto>> responseObject = new ResponseObject<>(categoryDetail);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

}



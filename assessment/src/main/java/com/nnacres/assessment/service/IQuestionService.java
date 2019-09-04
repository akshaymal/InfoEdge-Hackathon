package com.nnacres.assessment.service;


import com.nnacres.assessment.dto.QuestionModel;
import com.nnacres.assessment.dto.RandomQuestionDTO;

import java.util.List;

public interface IQuestionService {
    QuestionModel saveQuestion(QuestionModel question);
    List<QuestionModel> getQuestion();
    List<List<QuestionModel>> getFixedQuestion(RandomQuestionDTO randomQuestionDTO);
    QuestionModel findOne(Long id);
    QuestionModel getQuestionById(Long questionId);
    List<QuestionModel> getAllQuestions();

}

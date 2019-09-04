package com.nnacres.assessment.repository;

import com.nnacres.assessment.entity.QuestionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface QuestionResponseRepository extends JpaRepository<QuestionResponse, Long> {}

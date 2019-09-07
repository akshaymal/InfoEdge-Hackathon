package com.nnacres.assessment.repository;

import com.nnacres.assessment.entity.ContestQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ContestQuestionRepository extends JpaRepository<ContestQuestion, Long> {
	public List<ContestQuestion> findBycontestId(Long contestId);
}

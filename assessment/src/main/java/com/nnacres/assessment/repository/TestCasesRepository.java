package com.nnacres.assessment.repository;

import com.nnacres.assessment.entity.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestCasesRepository extends JpaRepository<TestCase,Integer> {

}

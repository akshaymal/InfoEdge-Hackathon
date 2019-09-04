package com.nnacres.assessment.repository;

import com.nnacres.assessment.entity.Contest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Vishal on 19/12/17.
 */
@Repository
public interface ContestRepository extends JpaRepository<Contest, Long> {

}

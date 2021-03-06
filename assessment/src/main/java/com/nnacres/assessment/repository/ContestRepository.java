package com.nnacres.assessment.repository;

import com.nnacres.assessment.entity.Contest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ContestRepository extends JpaRepository<Contest, Long> {

    Optional<Contest> findById(final Long id);

}

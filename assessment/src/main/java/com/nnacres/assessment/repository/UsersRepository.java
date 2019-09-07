package com.nnacres.assessment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nnacres.assessment.entity.Question;
import com.nnacres.assessment.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users,Integer> {

   Optional<List<Users>> findByUsername(final String username);

}

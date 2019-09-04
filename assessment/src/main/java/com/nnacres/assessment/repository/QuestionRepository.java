package com.nnacres.assessment.repository;


import com.nnacres.assessment.entity.Question;
import com.nnacres.assessment.enums.DifficultyLevel;
import com.nnacres.assessment.enums.QuestionType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    Optional<Question> findById(final Long id);

    @Query(value="select p from Question p")
    List<Question> find(Pageable pageable);

    @Query(value="select p from Question p,Category c Where c.id =p.id and p.difficultyLevel=:difficultyLevel and c.name=:categoryName and p.type=:questionType")
    List<Question> findByCategoryNameAndDifficultyLevelAndType(@Param("categoryName") String categoryName, @Param("difficultyLevel") DifficultyLevel difficultyLevel, @Param("questionType") QuestionType questionType, Pageable pageRequest);

}

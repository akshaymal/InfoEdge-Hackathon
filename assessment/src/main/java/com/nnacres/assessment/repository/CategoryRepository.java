package com.nnacres.assessment.repository;

import com.nnacres.assessment.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN false ELSE true END FROM Category c WHERE lower(c.name) = :name")
    boolean NotExistsByName(@Param("name") String category);

}

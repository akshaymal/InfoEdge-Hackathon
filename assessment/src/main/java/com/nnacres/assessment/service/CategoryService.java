package com.nnacres.assessment.service;



import com.nnacres.assessment.dto.CategoryDto;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    List<CategoryDto> addOption(Set<CategoryDto> categoryDtos);
    List<CategoryDto> getCategories();
}

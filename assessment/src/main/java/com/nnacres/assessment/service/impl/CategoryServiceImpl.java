package com.nnacres.assessment.service.impl;

import com.nnacres.assessment.dto.CategoryDto;
import com.nnacres.assessment.entity.Category;
import com.nnacres.assessment.exception.GenericException;
import com.nnacres.assessment.repository.CategoryRepository;
import com.nnacres.assessment.service.CategoryService;
import org.apache.commons.collections.CollectionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @Transactional(rollbackOn = GenericException.class)
    public List<CategoryDto> addOption(Set<CategoryDto> categoryDtos) {
        Timestamp timestamp = Timestamp.from(Instant.now());
        List<Category> categories = new ArrayList<>();
        List<CategoryDto> categoryDtosSet = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(categoryDtos)) {
            categoryDtos.parallelStream().forEach(dto -> {

                Category category =
                    Category.builder().name(dto.getName()).build();

                category.setCreatedDate(timestamp);
                category.setUpdatedDate(timestamp);
                
                final Category storedCategory = categoryRepository.findByName(category.getName().toLowerCase());

                if(Objects.isNull(storedCategory))
                {
                    categories.add(category);
                }
                else {
                   categoryDtosSet.add(dto.convertCategory(storedCategory));
                }
                ;
            });

            List<Category> responseCategories = categoryRepository.saveAll(categories);

            if (CollectionUtils.isNotEmpty(responseCategories)) {
                for (Category category : responseCategories) {
                    CategoryDto dto = new CategoryDto();
                    categoryDtosSet.add(dto.convertCategory(category));
                }

            }

        }
        return categoryDtosSet;
    }


    @Override
    @Transactional(rollbackOn = GenericException.class)
    public List<CategoryDto> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(categories)) {
            categories.parallelStream().forEach(category -> {
                CategoryDto categoryDto = CategoryDto.builder().name(category.getName()).build();
                categoryDtos.add(categoryDto);
            });

        }
        return categoryDtos;


    }
}


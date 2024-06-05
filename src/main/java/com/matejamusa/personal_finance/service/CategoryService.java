package com.matejamusa.personal_finance.service;

import com.matejamusa.personal_finance.exception.ApiException;
import com.matejamusa.personal_finance.model.Category;
import com.matejamusa.personal_finance.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository<Category> categoryRepository;

    public Category createCategoryForUser(Category category, Long userId) {
        return categoryRepository.createCategoryForUser(category, userId);
    }

    public Category getById(Long id) {
        return categoryRepository.getById(id);
    }

    public Category createOrGetCorrectionCategory(Category category, Long userId) {
        String type = category.getType().equals("EXPENSE") ? "INCOME" : "EXPENSE";

        Category correctedCategory;
        Integer count = categoryRepository.getCategoryCountByNameAndType(category.getName(), type);
        if (count == 0) {
            category.setType(type);
            correctedCategory = categoryRepository.createCategoryForUser(category, userId);
        } else if (count == 1) {
            correctedCategory = categoryRepository.getByNameAndType(category.getName(), type);
        } else {
            throw new ApiException("Category not found");
        }

        return correctedCategory;

    }
}

package com.matejamusa.personal_finance.service;

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
}

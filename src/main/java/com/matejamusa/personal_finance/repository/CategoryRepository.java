package com.matejamusa.personal_finance.repository;

import com.matejamusa.personal_finance.model.Category;

public interface CategoryRepository<T extends Category> {
    T createCategoryForUser(T category, Long userId);
}

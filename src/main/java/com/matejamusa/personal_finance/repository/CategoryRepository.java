package com.matejamusa.personal_finance.repository;

import com.matejamusa.personal_finance.model.Category;
import com.matejamusa.personal_finance.rowmapper.CategoryRowMapper;

import java.util.List;
import java.util.Map;

import static com.matejamusa.personal_finance.query.CategoryQuery.*;

public interface CategoryRepository<T extends Category> {
    T createCategoryForUser(T category, Long userId);
    T getById(long id);
    Integer getCategoryCountByName(String categoryName);
    Integer getCategoryCountByNameAndType(String name, String type);
    T getByNameAndType(String name, String type);
    List<T> getAllByUserId(Long id);
}

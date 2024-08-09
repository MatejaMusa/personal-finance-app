package com.matejamusa.personal_finance.repository.impl;

import com.matejamusa.personal_finance.exception.ApiException;
import com.matejamusa.personal_finance.model.Category;
import com.matejamusa.personal_finance.model.User;
import com.matejamusa.personal_finance.repository.CategoryRepository;
import com.matejamusa.personal_finance.rowmapper.CategoryRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.matejamusa.personal_finance.query.CategoryQuery.*;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CategoryRepositoryImpl implements CategoryRepository<Category> {
    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public Category createCategoryForUser(Category category, Long userId) {
        Category newCategory;
        Integer count = getCategoryCountByNameAndType(category.getName(), category.getType());
        if (count == null || count == 0) {
            newCategory = createCategory(category);
        } else {
            newCategory = getByNameAndType(category.getName(), category.getType());
        }
        createUserCategoryIfNotExist(userId, newCategory.getId());
        return newCategory;
    }

    public void createUserCategoryIfNotExist(Long userId, Long categoryId) {
        try {
            Integer count = jdbc.queryForObject(COUNT_USER_CATEGORY_BY_USER_ID_AND_CATEGORY_ID_QUERY, Map.of("userId", userId, "categoryId", categoryId), Integer.class);
            if (count == null || count == 0) {
                jdbc.update(CREATE_USER_CATEGORY_QUERY, Map.of("userId", userId, "categoryId", categoryId));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public Category getById(long id) {
        try {
            return jdbc.queryForObject(SELECT_CATEGORY_BY_ID, Map.of("id", id), new CategoryRowMapper());
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
            throw new ApiException("No Category found by id: " + id);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public Category getByNameAndType(String name, String type) {
        try {
            return jdbc.queryForObject(SELECT_CATEGORY_BY_NAME_AND_TYPE_QUERY, Map.of("name", name, "type", type), new CategoryRowMapper());
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
            throw new ApiException("No Category found by name: " + name + " and type: " + type);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public List<Category> getAllByUserId(Long id) {
        try {

            return jdbc.query(SELECT_ALL_CATEGORIES_FOR_USER_ID_QUERY, Map.of("userId", id), new CategoryRowMapper());
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
            throw new ApiException("No categories found");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public Integer getCategoryCountByName(String categoryName) {
        try {
            return jdbc.queryForObject(COUNT_CATEGORY_BY_NAME_QUERY, Map.of("name", categoryName), Integer.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public Integer getCategoryCountByNameAndType(String name, String type) {
        try {
            return jdbc.queryForObject(COUNT_CATEGORY_BY_NAME_AND_TYPE_QUERY, Map.of("name", name, "type", type), Integer.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    private Category createCategory(Category category) {
        try {
            KeyHolder holder = new GeneratedKeyHolder();
            jdbc.update(CREATE_CATEGORY_QUERY, getSqlParameterSource(category), holder);
            category.setId(requireNonNull(holder.getKey()).longValue());
            return category;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    private SqlParameterSource getSqlParameterSource(Category category) {
        return new MapSqlParameterSource()
                .addValue("name", category.getName())
                .addValue("type", category.getType());
    }
}

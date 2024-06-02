package com.matejamusa.personal_finance.rowmapper;

import com.matejamusa.personal_finance.model.Category;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryRowMapper implements RowMapper<Category> {
    @Override
    public Category mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        return Category.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .type(resultSet.getString("type"))
                .build();
    }
}
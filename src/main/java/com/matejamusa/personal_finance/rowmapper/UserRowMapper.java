package com.matejamusa.personal_finance.rowmapper;

import com.matejamusa.personal_finance.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        return User.builder()
                .id(resultSet.getLong("id"))
                .username(resultSet.getString("username"))
                .password(resultSet.getString("password"))
                .build();
    }
}

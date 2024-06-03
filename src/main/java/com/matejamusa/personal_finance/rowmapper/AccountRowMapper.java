package com.matejamusa.personal_finance.rowmapper;

import com.matejamusa.personal_finance.model.Account;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRowMapper implements RowMapper<Account> {
    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Account.builder().
                id(rs.getLong("id")).
                userId(rs.getLong("user_id")).
                name(rs.getString("name")).
                description(rs.getString("description")).
                priority(rs.getString("priority")).
                balance(rs.getFloat("balance")).
                build();
    }
}

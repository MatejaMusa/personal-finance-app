package com.matejamusa.personal_finance.rowmapper;

import com.matejamusa.personal_finance.model.Transaction;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionRowMapper implements RowMapper<Transaction> {

    @Override
    public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Transaction.builder().
                id(rs.getLong("id")).
                accountId(rs.getLong("account_id")).
                categoryId(rs.getLong("category_id")).
                amount(rs.getDouble("amount")).
                transactionDate(rs.getTimestamp("transaction_date").toLocalDateTime()).
                build();
    }
}

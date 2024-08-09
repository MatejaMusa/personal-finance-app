package com.matejamusa.personal_finance.rowmapper;

import com.matejamusa.personal_finance.dto.TransactionDTO;
import com.matejamusa.personal_finance.model.Transaction;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionDTORowMapper implements RowMapper<TransactionDTO> {

    @Override
    public TransactionDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return TransactionDTO.builder().
                id(rs.getLong("id")).
                categoryName(rs.getString("category_name")).
                amount(rs.getDouble("amount")).
                type(rs.getString("type")).
                transactionDate(rs.getTimestamp("transaction_date").toLocalDateTime()).
                build();
    }
}

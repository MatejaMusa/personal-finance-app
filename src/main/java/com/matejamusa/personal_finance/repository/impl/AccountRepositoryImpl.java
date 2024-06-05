package com.matejamusa.personal_finance.repository.impl;

import com.matejamusa.personal_finance.model.Account;
import com.matejamusa.personal_finance.repository.AccountRepository;
import com.matejamusa.personal_finance.rowmapper.AccountRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.matejamusa.personal_finance.query.AccountQuery.*;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository<Account> {
    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public void create(Account account, long userId) {
        SqlParameterSource params = new MapSqlParameterSource().
                addValue("userId", userId).
                addValue("name",account.getName()).
                addValue("description", account.getDescription()).
                addValue("priority", account.getPriority());
        jdbc.update(CREATE_ACCOUNT_QUERY, params);
    }

    @Override
    public Account getById(long id) {
        return jdbc.queryForObject(SELECT_ACCOUNT_BY_ID_QUERY, Map.of("id", id), new AccountRowMapper());
    }

    @Override
    public List<Account> getAllByUserId(long userId) {
        return jdbc.query(SELECT_ACCOUNTS_BY_USER_ID_QUERY, Map.of("userId", userId), new AccountRowMapper());
    }

    @Override
    public void updateBalance(long accountId, double amount, String type) {
        SqlParameterSource params = new MapSqlParameterSource().
                addValue("accountId", accountId).
                addValue("amount", amount)
                .addValue("type", type);
        jdbc.update(UPDATE_ACCOUNT_BALANCE_QUERY, params);
    }
}

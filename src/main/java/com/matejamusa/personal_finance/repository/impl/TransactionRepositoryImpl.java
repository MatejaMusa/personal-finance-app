package com.matejamusa.personal_finance.repository.impl;

import com.matejamusa.personal_finance.model.Account;
import com.matejamusa.personal_finance.model.Category;
import com.matejamusa.personal_finance.model.Transaction;
import com.matejamusa.personal_finance.repository.AccountRepository;
import com.matejamusa.personal_finance.repository.CategoryRepository;
import com.matejamusa.personal_finance.repository.TransactionRepository;
import com.matejamusa.personal_finance.rowmapper.TransactionRowMapper;
import com.matejamusa.personal_finance.service.AccountService;
import com.matejamusa.personal_finance.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static com.matejamusa.personal_finance.query.TransactionQuery.*;

@Repository
@RequiredArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository<Transaction>{
    private final NamedParameterJdbcTemplate jdbc;
    private final AccountRepository<Account> accountRepository;
    private final CategoryRepository<Category> categoryRepository;

    @Override
    @Transactional
    public void create(Transaction transaction, Category category) {
        SqlParameterSource params = new MapSqlParameterSource().
                addValue("accountId", transaction.getAccountId()).
                addValue("categoryId", transaction.getCategoryId()).
                addValue("amount", transaction.getAmount()).
                addValue("transactionDate", transaction.getTransactionDate());
        jdbc.update(CREATE_TRANSACTION_QUERY, params);
        accountRepository.updateBalance(transaction.getAccountId(), transaction.getAmount(), category.getType());
    }

    @Override
    public Page<Transaction> findAllByAccountId(Long accountId, Pageable pageable) {
        List<Transaction> transactions = jdbc.query(SELECT_TRANSACTIONS_BY_ACCOUNT_ID_PAGEABLE_QUERY, Map.of("accountId", accountId, "limit", pageable.getPageSize(), "offset", pageable.getOffset()), new TransactionRowMapper());
        Integer total = jdbc.queryForObject(COUNT_TRANSACTIONS_BY_ACCOUNT_ID_QUERY, Map.of("accountId", accountId), Integer.class);

        int totalTransactionsByAccountId = (total != null) ? total : 0;

        return new PageImpl<>(transactions, pageable, totalTransactionsByAccountId);
    }

    @Override
    public Transaction findById(Long id) {
        return jdbc.queryForObject(SELECT_TRANSACTION_BY_ID, Map.of("id", id), new TransactionRowMapper());
    }
}

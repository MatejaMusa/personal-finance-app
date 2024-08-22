package com.matejamusa.personal_finance.repository.impl;

import com.matejamusa.personal_finance.enums.GraphType;
import com.matejamusa.personal_finance.dto.TransactionDTO;
import com.matejamusa.personal_finance.exception.ApiException;
import com.matejamusa.personal_finance.model.Account;
import com.matejamusa.personal_finance.model.Category;
import com.matejamusa.personal_finance.model.GraphPoint;
import com.matejamusa.personal_finance.model.Transaction;
import com.matejamusa.personal_finance.repository.AccountRepository;
import com.matejamusa.personal_finance.repository.CategoryRepository;
import com.matejamusa.personal_finance.repository.TransactionRepository;
import com.matejamusa.personal_finance.rowmapper.GraphPointRowMapper;
import com.matejamusa.personal_finance.rowmapper.TransactionDTORowMapper;
import com.matejamusa.personal_finance.rowmapper.TransactionRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static com.matejamusa.personal_finance.query.TransactionQuery.*;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TransactionRepositoryImpl implements TransactionRepository<Transaction> {
    private final NamedParameterJdbcTemplate jdbc;
    private final AccountRepository<Account> accountRepository;
    private final CategoryRepository<Category> categoryRepository;

    @Override
    @Transactional
    public void create(Transaction transaction, Category category) {
        KeyHolder holder = new GeneratedKeyHolder();
        try {
            SqlParameterSource params = new MapSqlParameterSource().
                    addValue("accountId", transaction.getAccountId()).
                    addValue("categoryId", category.getId()).
                    addValue("amount", transaction.getAmount()).
                    addValue("transactionDate", transaction.getTransactionDate());
            jdbc.update(CREATE_TRANSACTION_QUERY, params, holder);
            transaction.setId(requireNonNull(holder.getKey()).longValue());

            accountRepository.updateBalance(transaction.getAccountId(), transaction.getAmount(), category.getType());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public Page<TransactionDTO> findAllByAccountId(Long accountId, Pageable pageable) {
        try {
            List<TransactionDTO> transactions = jdbc.query(SELECT_TRANSACTIONS_BY_ACCOUNT_ID_PAGEABLE_QUERY, Map.of("accountId", accountId, "limit", pageable.getPageSize(), "offset", pageable.getOffset()), new TransactionDTORowMapper());
            Integer total = jdbc.queryForObject(COUNT_TRANSACTIONS_BY_ACCOUNT_ID_QUERY, Map.of("accountId", accountId), Integer.class);

            int totalTransactionsByAccountId = (total != null) ? total : 0;

            return new PageImpl<>(transactions, pageable, totalTransactionsByAccountId);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public List<GraphPoint> getGraphByTypeAndAccountId(Long accountId, GraphType type) {
        try {
            return jdbc.query(SELECT_GRAPH_BY_ACCOUNT_ID_AND_TYPE, Map.of("accountId", accountId, "type", type.getValue()), new GraphPointRowMapper());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public Transaction findById(Long id) {
        try {
            return jdbc.queryForObject(SELECT_TRANSACTION_BY_ID, Map.of("id", id), new TransactionRowMapper());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }
}

package com.matejamusa.personal_finance.repository;

import com.matejamusa.personal_finance.model.Category;
import com.matejamusa.personal_finance.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionRepository<T extends Transaction>{
    void create(T transaction, Category category);
    T findById(Long id);
    Page<Transaction> findAllByAccountId(Long accountId, Pageable pageable);
}

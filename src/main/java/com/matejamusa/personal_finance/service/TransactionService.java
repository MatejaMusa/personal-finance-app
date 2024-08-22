package com.matejamusa.personal_finance.service;

import com.matejamusa.personal_finance.enums.GraphType;
import com.matejamusa.personal_finance.dto.TransactionDTO;
import com.matejamusa.personal_finance.model.Account;
import com.matejamusa.personal_finance.model.Category;
import com.matejamusa.personal_finance.model.GraphPoint;
import com.matejamusa.personal_finance.model.Transaction;
import com.matejamusa.personal_finance.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository<Transaction> transactionRepository;
    private final CategoryService categoryService;
    private final AccountService accountService;

    public void create(Transaction transaction) {
        Category category = categoryService.getById(transaction.getCategoryId());
        transactionRepository.create(transaction, category);
    }

    public Page<TransactionDTO> findAllByAccountId(Long accountId, Pageable pageable) {
        return transactionRepository.findAllByAccountId(accountId, pageable);
    }

    public Transaction findById(Long id) {
        return transactionRepository.findById(id);
    }

    public List<GraphPoint> getGraphByTypeAndAccountId(Long accountId, GraphType type) {
        return transactionRepository.getGraphByTypeAndAccountId(accountId, type);
    }

    public void correctTransaction(Long transactionId) {
        Transaction transaction = findById(transactionId);
        Account account = accountService.getById(transaction.getAccountId());
        Category category = categoryService.getById(transaction.getCategoryId());

        Category correctionCategory = categoryService.createOrGetCorrectionCategory(category, account.getUserId());

        transactionRepository.create(transaction, correctionCategory);
    }
}

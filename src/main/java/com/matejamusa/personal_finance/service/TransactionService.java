package com.matejamusa.personal_finance.service;

import com.matejamusa.personal_finance.model.Account;
import com.matejamusa.personal_finance.model.Category;
import com.matejamusa.personal_finance.model.Transaction;
import com.matejamusa.personal_finance.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository<Transaction> transactionRepository;
    private final CategoryService categoryService;
    private final AccountService accountService;

    public void create(Transaction transaction) {
        System.out.println("2");
        System.out.println(transaction.toString());
        Category category = categoryService.getById(transaction.getCategoryId());
        System.out.println("3");
        System.out.println(category);
        transactionRepository.create(transaction, category);
    }

    public Page<Transaction> findAllByAccountId(Long accountId, Pageable pageable) {
        return transactionRepository.findAllByAccountId(accountId, pageable);
    }

    public Transaction findById(Long id) {
        return transactionRepository.findById(id);
    }

    public void correctTransaction(Long transactionId) {
        Transaction transaction = findById(transactionId);
        System.out.println(transaction);
        Account account = accountService.getById(transaction.getAccountId());
        System.out.println(account);
        Category category = categoryService.getById(transaction.getCategoryId());
        System.out.println(category);

        Category correctionCategory = categoryService.createOrGetCorrectionCategory(category, account.getUserId());
        System.out.println(correctionCategory);

        transactionRepository.create(transaction, correctionCategory);
    }
}

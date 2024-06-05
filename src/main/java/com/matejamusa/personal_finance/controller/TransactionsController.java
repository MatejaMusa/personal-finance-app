package com.matejamusa.personal_finance.controller;

import com.matejamusa.personal_finance.model.Account;
import com.matejamusa.personal_finance.model.Transaction;
import com.matejamusa.personal_finance.model.User;
import com.matejamusa.personal_finance.service.AccountService;
import com.matejamusa.personal_finance.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TransactionsController {
    private final TransactionService transactionService;
    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<Page<Transaction>> getTransactions(Authentication authentication, @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size) {
        User user = (User) authentication.getPrincipal();
        Page<Transaction> transactions = transactionService.findAllByAccountId(user.getId(), PageRequest.of(page.orElse(0), size.orElse(10)));
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getById(Authentication authentication, @PathVariable Long id) {
        User user = (User) authentication.getPrincipal();
        Transaction transaction = transactionService.findById(id);
        Account account = accountService.getById(transaction.getAccountId());
//        if (!Objects.equals(user.getId(), account.getUserId())) {
//        }
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createTransaction(@RequestBody Transaction transaction) {
        transactionService.create(transaction);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/correct/{id}")
    public ResponseEntity<Void> correctTransaction(@PathVariable Long id) {
        transactionService.correctTransaction(id);
        return ResponseEntity.ok().build();
    }
}

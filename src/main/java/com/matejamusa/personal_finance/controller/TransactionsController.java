package com.matejamusa.personal_finance.controller;

import com.matejamusa.personal_finance.GraphType;
import com.matejamusa.personal_finance.dto.TransactionDTO;
import com.matejamusa.personal_finance.exception.ApiException;
import com.matejamusa.personal_finance.model.Account;
import com.matejamusa.personal_finance.model.GraphPoint;
import com.matejamusa.personal_finance.model.Transaction;
import com.matejamusa.personal_finance.model.User;
import com.matejamusa.personal_finance.service.AccountService;
import com.matejamusa.personal_finance.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TransactionsController {
    private final TransactionService transactionService;
    private final AccountService accountService;

    @GetMapping("/transactions/{accountId}")
    public ResponseEntity<Page<TransactionDTO>> getTransactions(Authentication authentication,@PathVariable Long accountId, @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size) {
        User user = (User) authentication.getPrincipal();
        Account account = accountService.getById(accountId);

        if (account.getUserId() != user.getId()) {
            throw new ApiException("You are not owner of these transactions");
        }
        Page<TransactionDTO> transactions = transactionService.findAllByAccountId(accountId, PageRequest.of(page.orElse(0), size.orElse(10)));
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/graphs/{accountId}")
    public ResponseEntity<Map<String, List<GraphPoint>>> getIncomeAndExpensesGraph(Authentication authentication, @PathVariable Long accountId) {
        User user = (User) authentication.getPrincipal();
        Account account = accountService.getById(accountId);

        if (account.getUserId() != user.getId()) {
            throw new ApiException("You are not owner of these transactions");
        }

        List<GraphPoint> expenseGraph = transactionService.getGraphByTypeAndAccountId(accountId, GraphType.EXPENSE);
        List<GraphPoint> incomeGraph = transactionService.getGraphByTypeAndAccountId(accountId, GraphType.INCOME);

        return ResponseEntity.ok(Map.of("income", incomeGraph, "expense", expenseGraph));
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
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        transactionService.create(transaction);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/correct/{id}")
    public ResponseEntity<Void> correctTransaction(@PathVariable Long id) {
        transactionService.correctTransaction(id);
        return ResponseEntity.ok().build();
    }
}

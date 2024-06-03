package com.matejamusa.personal_finance.controller;

import com.matejamusa.personal_finance.model.Account;
import com.matejamusa.personal_finance.model.User;
import com.matejamusa.personal_finance.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/{id}")
    public ResponseEntity<Account> getById(@PathVariable Long id) {
        Account account = accountService.getById(id);
        return ResponseEntity.ok(account);
    }

    @PostMapping("/create")
    public ResponseEntity<Void> create(Authentication authentication, @RequestBody Account account) {
        User user = (User) authentication.getPrincipal();
        accountService.create(account, user.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAllByUserId(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<Account> accounts = accountService.getAllByUserId(user.getId());
        return ResponseEntity.ok(accounts);
    }
}

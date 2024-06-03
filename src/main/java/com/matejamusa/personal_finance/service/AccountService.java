package com.matejamusa.personal_finance.service;

import com.matejamusa.personal_finance.model.Account;
import com.matejamusa.personal_finance.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository<Account> accountRepository;

    public void create(Account account, long userId) {
        accountRepository.create(account, userId);
    }

    public Account getById(long id) {
        return accountRepository.getById(id);
    }

    public List<Account> getAllByUserId(long userId) {
        return accountRepository.getAllByUserId(userId);
    }
}

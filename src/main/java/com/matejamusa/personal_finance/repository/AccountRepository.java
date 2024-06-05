package com.matejamusa.personal_finance.repository;

import com.matejamusa.personal_finance.model.Account;

import java.util.List;

public interface AccountRepository<T extends Account> {
    void create(T account, long userId);
    T getById(long id);
    List<T> getAllByUserId(long userId);
    void updateBalance(long accountId, double amount, String type);
}

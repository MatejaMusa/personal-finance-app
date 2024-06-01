package com.matejamusa.personal_finance.repository;

import com.matejamusa.personal_finance.model.User;

public interface UserRepository<T extends User> {
    T get(Long id);

    User getUserByUsername(String username);
    T create(T user);

}

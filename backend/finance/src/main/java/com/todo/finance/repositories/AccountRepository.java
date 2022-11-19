package com.todo.finance.repositories;

import com.todo.finance.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    public Account findAccountByNumber(String number);
    
}

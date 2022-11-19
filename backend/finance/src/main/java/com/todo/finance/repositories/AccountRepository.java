package com.todo.finance.repositories;

import com.todo.finance.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Transactional
    public Account findAccountByNumber(String number);
    
}

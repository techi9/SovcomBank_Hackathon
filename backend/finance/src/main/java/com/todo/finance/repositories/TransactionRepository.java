package com.todo.finance.repositories;

import com.todo.finance.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(isolation = Isolation.REPEATABLE_READ)
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

}

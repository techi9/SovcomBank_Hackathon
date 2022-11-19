package com.todo.finance.repositories;

import com.todo.finance.enumerations.TransactionTaskStatus;
import com.todo.finance.model.TransactionTask;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionTaskRepository extends JpaRepository<TransactionTask, Integer> {
    List<TransactionTask> findAllByTaskStatus(TransactionTaskStatus status, Pageable pageable);
    List<TransactionTask> getAllByTaskStatus(TransactionTaskStatus status);
}

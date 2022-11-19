package com.todo.worker.config;

import com.todo.finance.enumerations.TransactionTaskStatus;
import com.todo.finance.model.Account;
import com.todo.finance.model.Transaction;
import com.todo.finance.model.TransactionTask;
import com.todo.finance.repositories.AccountRepository;
import com.todo.finance.repositories.TransactionRepository;
import com.todo.finance.repositories.TransactionTaskRepository;
import com.todo.finance.services.MappingTransactionTaskToTransaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@EnableRetry
@Component
public class TransactionExecutionPayload {

    @Autowired
    private TransactionTaskRepository taskRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    private final MappingTransactionTaskToTransaction mappingTransactionTaskToTransaction;

    public TransactionExecutionPayload() {
        mappingTransactionTaskToTransaction = new MappingTransactionTaskToTransaction();
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRES_NEW)
    @Retryable(maxAttempts = 10, backoff = @Backoff(multiplier = 1.5, random = true, delay = 500))
    public void createTransaction(TransactionTask transactionTask) {
        Transaction transaction = mappingTransactionTaskToTransaction.mapToTransaction(transactionTask);

        Account accountFrom = accountRepository.findAccountByNumber(transactionTask.getAccountNumberFrom());
        Account accountTo = accountRepository.findAccountByNumber(transactionTask.getAccountNumberTo());
        accountFrom.setValue(accountFrom.getValue() - transactionTask.getValue()*transactionTask.getExchangeRate());
        accountTo.setValue(accountTo.getValue() + transactionTask.getValue()*transactionTask.getExchangeRate());

        accountRepository.save(accountFrom);
        accountRepository.save(accountTo);

        transaction.setAccountFrom(accountFrom);
        transaction.setAccountTo(accountTo);
        transaction.addUser(accountFrom.getUser());
        transaction.addUser(accountTo.getUser());

        transactionRepository.save(transaction);

        transactionTask.setTaskStatus(TransactionTaskStatus.FINISHED);
        taskRepository.save(transactionTask);

    }

    @Recover
    public void recover() {
        log.error("Transaction failed");
    }
}

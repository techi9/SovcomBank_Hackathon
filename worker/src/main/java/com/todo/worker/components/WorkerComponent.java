package com.todo.worker.components;

import com.todo.finance.enumerations.TransactionTaskStatus;
import com.todo.finance.model.Account;
import com.todo.finance.model.Transaction;
import com.todo.finance.model.TransactionTask;
import com.todo.finance.repositories.AccountRepository;
import com.todo.finance.repositories.TransactionRepository;
import com.todo.finance.repositories.TransactionTaskRepository;
import com.todo.finance.repositories.UserRepository;
import com.todo.finance.services.MappingTransactionTaskToTransaction;
import com.todo.worker.config.ExecutorConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


@EnableAsync
@Component
@Slf4j
public class WorkerComponent {
    @Autowired
    private TransactionTaskRepository taskRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;

    private final ThreadPoolTaskExecutor executor;
    private final MappingTransactionTaskToTransaction mappingTransactionTaskToTransaction;

    public WorkerComponent() {
        executor = (ThreadPoolTaskExecutor)  new AnnotationConfigApplicationContext(ExecutorConfiguration.class)
                .getBean("taskExecutor");
        mappingTransactionTaskToTransaction = new MappingTransactionTaskToTransaction();
    }

    @PostConstruct
    void run () {
        log.info("check");
        log.info(String.valueOf(accountRepository.findAll().size()));
        log.info(String.valueOf(userRepository.findAll().size()));
        log.info(String.valueOf(transactionRepository.findAll().size()));
    }

    @Scheduled(fixedRate = 5000)
    public void checkTransactionsTasks() {
        System.out.println("here");
        int num = executor.getMaxPoolSize() - executor.getActiveCount();
        if(num > 0) {
            List<TransactionTask> transactionTasks = pollTasks(num);
            transactionTasks.forEach((elem) -> {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        log.info("start to create with {}", elem);
                        createTransaction(elem);
                    }
                });
            });
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public List<TransactionTask> pollTasks(int taskNumber) {
        Pageable pageable = PageRequest.of(1, taskNumber);
        List<TransactionTask> transactionTasks = taskRepository.findAllByTaskStatus(TransactionTaskStatus.QUEUED, pageable);
        List<TransactionTask> test1 = taskRepository.findAll();
        log.info(String.valueOf(test1.size()));
        log.info(String.valueOf(accountRepository.findAll().size()));
        log.info(String.valueOf(userRepository.findAll().size()));
        log.info(String.valueOf(transactionRepository.findAll().size()));
        transactionTasks.forEach((elem) -> {
            elem.setTaskStatus(TransactionTaskStatus.RUNNING);
            taskRepository.save(elem);
        });
        return transactionTasks;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void createTransaction(TransactionTask transactionTask) {
        log.info(transactionTask.toString());
        Transaction transaction = mappingTransactionTaskToTransaction.mapToTransaction(transactionTask);
        Account accountFrom = accountRepository.findAccountByNumber(transactionTask.getAccountNumberFrom());
        Account accountTo = accountRepository.findAccountByNumber(transactionTask.getAccountNumberTo());
        accountFrom.setValue(accountFrom.getValue() - transactionTask.getValue()*transactionTask.getExchangeRate());
        accountTo.setValue(accountTo.getValue() + transactionTask.getValue()*transactionTask.getExchangeRate());

        transaction.setUsers(new HashSet<>(Arrays.asList(accountFrom.getUser(), accountTo.getUser())));
        log.info(String.valueOf(transaction.getUsers().size()));
        transactionRepository.save(transaction);

        transactionTask.setTaskStatus(TransactionTaskStatus.FINISHED);
        taskRepository.save(transactionTask);

    }




}

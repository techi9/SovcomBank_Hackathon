package com.todo.worker.components;

import com.todo.finance.enumerations.TransactionTaskStatus;
import com.todo.finance.model.TransactionTask;
import com.todo.finance.repositories.TransactionTaskRepository;
import com.todo.worker.config.ExecutorConfiguration;
import com.todo.worker.config.TransactionExecutionPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@EnableAsync
@Component
@Slf4j
public class WorkerComponent {
    @Autowired
    private TransactionTaskRepository taskRepository;

    private final ThreadPoolTaskExecutor executor;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    public WorkerComponent() {
        executor = (ThreadPoolTaskExecutor)  new AnnotationConfigApplicationContext(ExecutorConfiguration.class)
                .getBean("taskExecutor");
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
                        (applicationContext.getBean(TransactionExecutionPayload.class)).createTransaction(elem);
                    }
                });
            });
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public List<TransactionTask> pollTasks(int taskNumber) {
        Pageable pageable = PageRequest.of(0, taskNumber);
        List<TransactionTask> transactionTasks = taskRepository.findAllByTaskStatus(TransactionTaskStatus.QUEUED, pageable);
        List<TransactionTask> test1 = taskRepository.findAllByTaskStatus(TransactionTaskStatus.QUEUED);
        transactionTasks.forEach((elem) -> {
            elem.setTaskStatus(TransactionTaskStatus.RUNNING);
            taskRepository.save(elem);
        });
        return transactionTasks;
    }


}

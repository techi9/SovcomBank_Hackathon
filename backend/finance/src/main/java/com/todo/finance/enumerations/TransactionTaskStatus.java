package com.todo.finance.enumerations;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum TransactionTaskStatus {
    QUEUED(0),
    RUNNING(1),
    FINISHED(2);

    private final int taskStatus;

    TransactionTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }
}

package com.todo.finance.services.mappers;

import com.todo.finance.model.Transaction;
import com.todo.finance.model.TransactionTask;
import org.modelmapper.ModelMapper;

public class MappingTransactionTaskToTransaction extends ModelMapper {
    public Transaction mapToTransaction(TransactionTask transactionTask) {
        return map(transactionTask, Transaction.class);
    }
}

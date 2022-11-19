package com.todo.finance.model;

import com.todo.finance.enumerations.TransactionTaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction_queue")
public class TransactionTask {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String accountNumberTo;
    private String accountNumberFrom;

    private Date date;
    private double value;
    private double exchangeRate;

    private UUID idempotencyToken;
    private TransactionTaskStatus taskStatus;
}

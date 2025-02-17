package com.tulio.banksofkareactivo.events;

import com.tulio.banksofkareactivo.models.TransactionType;

import java.time.LocalDateTime;

public class TransactionEvent extends Event{

    private String userId;
    private TransactionType transactionType;
    private String status;
    private Double initialBalance;
    private Double finalBalance;
    private Double amount;
    private LocalDateTime date;
    private String message;

    public TransactionEvent() {
        super();
    }

    public TransactionEvent(String userId, TransactionType transactionType, String status, Double initialBalance, Double finalBalance, Double amount, LocalDateTime date, String message) {
        super();
        this.userId = userId;
        this.transactionType = transactionType;
        this.status = status;
        this.initialBalance = initialBalance;
        this.finalBalance = finalBalance;
        this.amount = amount;
        this.date = date;
        this.message = message;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public String getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }

    public Double getInitialBalance() {
        return initialBalance;
    }

    public Double getAmount() {
        return amount;
    }

    public Double getFinalBalance() {
        return finalBalance;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }
}

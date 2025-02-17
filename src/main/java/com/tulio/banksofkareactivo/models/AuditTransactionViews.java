package com.tulio.banksofkareactivo.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "transaction_views")
public class AuditTransactionViews {

    @Id
    private String userId;
    private TransactionType transactionType;
    private Double amount;
    private Double initialBalance;
    private Double finalBalance;
    private LocalDateTime date;

    public AuditTransactionViews(String userId, TransactionType transactionType, Double amount, Double initialBalance, Double finalBalance, LocalDateTime date) {
        this.userId = userId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.initialBalance = initialBalance;
        this.finalBalance = finalBalance;
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(Double initialBalance) {
        this.initialBalance = initialBalance;
    }

    public Double getFinalBalance() {
        return finalBalance;
    }

    public void setFinalBalance(Double finalBalance) {
        this.finalBalance = finalBalance;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}

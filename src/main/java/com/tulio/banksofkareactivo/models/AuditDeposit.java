package com.tulio.banksofkareactivo.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "audit_deposits")
public class AuditDeposit {

    @Id
    private String id;
    private String userId;
    private Double initialBalance;
    private Double depositAmount;
    private Double finalBalance;
    private LocalDateTime date;

    public AuditDeposit(String userId, Double initialBalance, Double depositAmount, Double finalBalance, LocalDateTime date) {
        this.userId = userId;
        this.initialBalance = initialBalance;
        this.depositAmount = depositAmount;
        this.finalBalance = finalBalance;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(Double initialBalance) {
        this.initialBalance = initialBalance;
    }

    public Double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(Double depositAmount) {
        this.depositAmount = depositAmount;
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
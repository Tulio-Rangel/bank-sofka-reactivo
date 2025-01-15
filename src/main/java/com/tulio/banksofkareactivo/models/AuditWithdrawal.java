package com.tulio.banksofkareactivo.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "audit_withdrawals")
public class AuditWithdrawal {

    @Id
    private String id;
    private String userId;
    private Double initialBalance;
    private Double withdrawalAmount;
    private String withdrawalType;
    private Double finalBalance;
    private LocalDateTime date;

    public AuditWithdrawal(String userId, Double initialBalance, Double withdrawalAmount, String withdrawalType, Double finalBalance, LocalDateTime date) {
        this.userId = userId;
        this.initialBalance = initialBalance;
        this.withdrawalAmount = withdrawalAmount;
        this.withdrawalType = withdrawalType;
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

    public Double getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public void setWithdrawalAmount(Double withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }

    public String getWithdrawalType() {
        return withdrawalType;
    }

    public void setWithdrawalType(String withdrawalType) {
        this.withdrawalType = withdrawalType;
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


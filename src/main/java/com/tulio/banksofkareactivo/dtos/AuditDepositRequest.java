package com.tulio.banksofkareactivo.dtos;

public class AuditDepositRequest {
    private String userId;
    private Double initialBalance;
    private Double depositAmount;
    private Double finalBalance;

    public AuditDepositRequest(String userId, Double initialBalance, Double depositAmount, Double finalBalance) {
        this.userId = userId;
        this.initialBalance = initialBalance;
        this.depositAmount = depositAmount;
        this.finalBalance = finalBalance;
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
}

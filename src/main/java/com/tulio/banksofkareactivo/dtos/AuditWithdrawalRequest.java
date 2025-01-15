package com.tulio.banksofkareactivo.dtos;

public class AuditWithdrawalRequest {
    private String userId;
    private Double initialBalance;
    private Double withdrawalAmount;
    private String withdrawalType;
    private Double finalBalance;

    public AuditWithdrawalRequest(String userId, Double initialBalance, Double withdrawalAmount, String withdrawalType, Double finalBalance) {
        this.userId = userId;
        this.initialBalance = initialBalance;
        this.withdrawalAmount = withdrawalAmount;
        this.withdrawalType = withdrawalType;
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
}

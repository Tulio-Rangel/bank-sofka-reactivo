package com.tulio.banksofkareactivo.events;

import java.time.LocalDateTime;

public class AccountCreatedEvent extends Event{

    private String userId;
    private String message;
    private String status;
    private LocalDateTime date;

    public AccountCreatedEvent(){
        super();
    }

    public AccountCreatedEvent(String userId, String message, String status, LocalDateTime date) {
        super();
        this.userId = userId;
        this.message = message;
        this.status = status;
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getDate() {
        return date;
    }
}

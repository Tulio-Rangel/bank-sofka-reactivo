package com.tulio.banksofkareactivo.events;

import com.tulio.banksofkareactivo.models.UserEventType;

import java.time.LocalDateTime;

public class UserEvent extends Event {

    private String email;
    private String message;
    private String status;
    private LocalDateTime date;
    private UserEventType userEventType;

    public UserEvent(String email, String message, String status, LocalDateTime date, UserEventType userEventType) {
        super();
        this.email = email;
        this.message = message;
        this.status = status;
        this.date = date;
        this.userEventType = userEventType;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public UserEventType getUserEventType() {
        return userEventType;
    }
}

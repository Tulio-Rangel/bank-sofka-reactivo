package com.tulio.banksofkareactivo.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "user_views")
public class AuditUserViews {

    @Id
    private String email;
    private LocalDateTime registeredAt;
    private LocalDateTime lastLogin;

    public AuditUserViews(String email, LocalDateTime registeredAt, LocalDateTime lastLogin) {
        this.email = email;
        this.registeredAt = registeredAt;
        this.lastLogin = lastLogin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }
}
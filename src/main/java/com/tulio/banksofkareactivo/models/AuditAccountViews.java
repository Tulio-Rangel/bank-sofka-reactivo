package com.tulio.banksofkareactivo.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "accounts_views")
public class AuditAccountViews {

    @Id
    private String userId;
    private LocalDateTime createdAt;

    public AuditAccountViews(String userId, LocalDateTime createdAt) {
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

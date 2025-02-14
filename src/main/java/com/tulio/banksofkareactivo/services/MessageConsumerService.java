package com.tulio.banksofkareactivo.services;

import java.util.Map;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumerService {
    private final AuditService auditService;

    public MessageConsumerService(AuditService auditService) {
        this.auditService = auditService;
    }

    @JmsListener(destination = "transaction.deposit")
    public void handleDeposit(Map<String, Object> message) {
        processMessage("transaction.deposit", message);
    }

    @JmsListener(destination = "transaction.withdrawal")
    public void handleWithdrawal(Map<String, Object> message) {
        processMessage("transaction.withdrawal", message);
    }

    @JmsListener(destination = "user.registration")
    public void handleUserRegistration(Map<String, Object> message) {
        processMessage("user.registration", message);
    }

    @JmsListener(destination = "user.login")
    public void handleUserLogin(Map<String, Object> message) {
        processMessage("user.login", message);
    }

    private void processMessage(String operation, Map<String, Object> message) {
        
        System.out.println("Evento recibido [" + operation + "]: " + message);
        
        String eventMessage = (String) message.get("message");
        String timestamp = (String) message.get("timestamp");
        String status = (String) message.get("status");

        // Guardar en auditoría
        //auditService.saveAudit(operation, eventMessage, timestamp, status);
    }
}
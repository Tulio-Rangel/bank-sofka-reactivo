package com.tulio.banksofkareactivo.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.tulio.banksofkareactivo.events.*;
import com.tulio.banksofkareactivo.models.TransactionType;
import com.tulio.banksofkareactivo.models.UserEventType;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;

@Service
public class MessageConsumerService {
    private final EventProcessor eventProcessor;

    public MessageConsumerService(EventProcessor eventProcessor) {
        this.eventProcessor = eventProcessor;
    }

    @JmsListener(destination = "transaction.deposit")
    public void handleDeposit(Map<String, Object> message) {
        processTransaction(TransactionType.DEPOSIT, message);
    }

    @JmsListener(destination = "transaction.withdrawal")
    public void handleWithdrawal(Map<String, Object> message) {
        processTransaction(TransactionType.WITHDRAWAL, message);
    }

    @JmsListener(destination = "user.registration")
    public void handleUserRegistration(Map<String, Object> message) {
        processUsers(UserEventType.REGISTER, message);
    }

    @JmsListener(destination = "account.creation")
    public void handleAccountRegistration(Map<String, Object> message) {
        processAccount("account.creation", message);
    }

    @JmsListener(destination = "user.login")
    public void handleUserLogin(Map<String, Object> message) {
        processUsers(UserEventType.LOGIN, message);
    }

    private void processAccount(String operation, Map<String, Object> message) {
        
        System.out.println("Evento recibido [" + operation + "]: " + message);

         String eventMessage = (String) message.get("message");
         String userId = (String) message.get("userId");
         LocalDateTime date =  LocalDateTime.parse((String) message.get("timestamp"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
         String status = (String) message.get("status");
         try {
            Event accountEvent = new AccountCreatedEvent(
                    userId,
                    eventMessage,
                    status,
                    date);
             eventProcessor.processAccount(accountEvent).subscribe();
         } catch (Exception e) {
             e.printStackTrace();
         }
    }

    private void processUsers(UserEventType userEventType, Map<String, Object> message){
        String eventMessage = (String) message.get("message");
        LocalDateTime date =  LocalDateTime.parse((String) message.get("timestamp"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String status = (String) message.get("status");
        String email = (String) message.get("email");
        try {
            Event userEvent = new UserEvent(
                    email,
                    eventMessage,
                    status,
                    date,
                    userEventType
            );
            eventProcessor.processUsers(userEvent).subscribe();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void processTransaction(TransactionType transactionType, Map<String, Object> message){
        HashMap<String, Object> transactionDetails = (HashMap<String, Object>) message.get("details");
        LocalDateTime date =  LocalDateTime.parse((String) message.get("timestamp"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String status = (String) message.get("status");
        String eventMessage = (String) message.get("message");
        try {
            Event transactionEvent = new TransactionEvent(
                    (String) transactionDetails.get("UserId"),
                    transactionType,
                    status,
                    (Double) transactionDetails.get("InitialBalance"),
                    (Double) transactionDetails.get("FinalBalance"),
                    (Double) transactionDetails.get("Amount"),
                    date,
                    eventMessage
                    );
            eventProcessor.processTransaction(transactionEvent).subscribe();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
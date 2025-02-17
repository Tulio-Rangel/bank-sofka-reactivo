package com.tulio.banksofkareactivo.events;

import com.tulio.banksofkareactivo.models.AuditAccounts;
import com.tulio.banksofkareactivo.models.AuditTransaction;
import com.tulio.banksofkareactivo.models.AuditUser;
import com.tulio.banksofkareactivo.repositories.AuditAccountRepository;
import com.tulio.banksofkareactivo.repositories.AuditTransactionRepository;
import com.tulio.banksofkareactivo.repositories.AuditUserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
public class EventProcessor {

    // Sinks para transmitir eventos en tiempo real
    private final Sinks.Many<AuditTransaction> transactionSink;
    private final Sinks.Many<AuditAccounts> accountsSink;
    private final Sinks.Many<AuditUser> usersSink;
    private final AuditTransactionRepository auditTransactionRepository;
    private final AuditAccountRepository accountRepository;
    private final AuditUserRepository userRepository;

    public EventProcessor(AuditTransactionRepository auditTransactionRepository, AuditAccountRepository accountRepository, AuditUserRepository userRepository) {
        this.auditTransactionRepository = auditTransactionRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.transactionSink = Sinks.many().multicast().onBackpressureBuffer();
        this.accountsSink = Sinks.many().multicast().onBackpressureBuffer();
        this.usersSink = Sinks.many().multicast().onBackpressureBuffer();
    }

    public Mono<AuditTransaction> processTransaction(Event event) {
            TransactionEvent transactionEvent = (TransactionEvent) event;
            AuditTransaction transaction = new AuditTransaction();
            transaction.setUserId(transactionEvent.getUserId());
            transaction.setInitialBalance(transactionEvent.getInitialBalance());
            transaction.setAmount(transactionEvent.getAmount());
            transaction.setFinalBalance(transactionEvent.getFinalBalance());
            transaction.setTransactionType(transactionEvent.getTransactionType());
            transaction.setDate(transactionEvent.getDate());
            transaction.setStatus(transactionEvent.getStatus());
            transaction.setMessage(transactionEvent.getMessage());
            return auditTransactionRepository.save(transaction)
                    .doOnSuccess(transactionSink::tryEmitNext);
    }

    public Mono<AuditAccounts> processAccount(Event event) {
        AccountCreatedEvent accountCreatedEvent = (AccountCreatedEvent) event;
        AuditAccounts account = new AuditAccounts();
        account.setUserId(accountCreatedEvent.getUserId());
        account.setMessage(accountCreatedEvent.getMessage());
        account.setDate(accountCreatedEvent.getDate());
        account.setStatus(accountCreatedEvent.getStatus());
        return accountRepository.save(account)
                .doOnSuccess(accountsSink::tryEmitNext);
    }

    public Mono<AuditUser> processUsers(Event event) {
        UserEvent userEvent = (UserEvent) event;
        AuditUser user = new AuditUser();
        user.setEmail(userEvent.getEmail());
        user.setUserEventType(userEvent.getUserEventType());
        user.setStatus(userEvent.getStatus());
        user.setMessage(userEvent.getMessage());
        user.setDate(userEvent.getDate());
        return userRepository.save(user)
                .doOnSuccess(usersSink::tryEmitNext);
    }



}

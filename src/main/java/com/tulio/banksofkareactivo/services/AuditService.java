package com.tulio.banksofkareactivo.services;

import com.tulio.banksofkareactivo.models.AuditTransaction;
import com.tulio.banksofkareactivo.models.TransactionType;
import com.tulio.banksofkareactivo.repositories.AuditTransactionRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.LocalDateTime;

@Service
public class AuditService {

    private final AuditTransactionRepository auditTransactionRepository;

    // Sinks para transmitir eventos en tiempo real
    private final Sinks.Many<AuditTransaction> transactionSink;

    public AuditService(AuditTransactionRepository auditTransactionRepository) {
        this.auditTransactionRepository = auditTransactionRepository;
        this.transactionSink = Sinks.many().multicast().onBackpressureBuffer();
    }

    // Registrar una auditoría de depósito
    public Mono<AuditTransaction> registerDeposit(String userId, Double initialBalance, Double depositAmount, Double finalBalance) {
        AuditTransaction transaction = new AuditTransaction();
        transaction.setUserId(userId);
        transaction.setInitialBalance(initialBalance);
        transaction.setAmount(depositAmount);
        transaction.setFinalBalance(finalBalance);
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setDate(LocalDateTime.now());
        return auditTransactionRepository.save(transaction)
                .doOnSuccess(transactionSink::tryEmitNext);
    }

    // Registrar una auditoría de retiro
    public Mono<AuditTransaction> registerWithdrawal(String userId, Double initialBalance, Double withdrawalAmount, Double finalBalance) {
        AuditTransaction transaction = new AuditTransaction();
        transaction.setUserId(userId);
        transaction.setInitialBalance(initialBalance);
        transaction.setAmount(withdrawalAmount);
        transaction.setFinalBalance(finalBalance);
        transaction.setTransactionType(TransactionType.WITHDRAWAL);
        transaction.setDate(LocalDateTime.now());
        return auditTransactionRepository.save(transaction)
                .doOnSuccess(transactionSink::tryEmitNext);
    }

    // Flujo de auditorías en tiempo real
    public Flux<AuditTransaction> streamTransactions() {
        return transactionSink.asFlux();
    }
}

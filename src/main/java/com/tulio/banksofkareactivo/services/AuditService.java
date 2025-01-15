package com.tulio.banksofkareactivo.services;


import com.tulio.banksofkareactivo.models.AuditDeposit;
import com.tulio.banksofkareactivo.models.AuditWithdrawal;
import com.tulio.banksofkareactivo.repositories.AuditDepositRepository;
import com.tulio.banksofkareactivo.repositories.AuditWithdrawalRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.LocalDateTime;

@Service
public class AuditService {

    private final AuditDepositRepository auditDepositRepository;
    private final AuditWithdrawalRepository auditWithdrawalRepository;

    // Sinks para transmitir eventos en tiempo real
    private final Sinks.Many<AuditDeposit> depositSink = Sinks.many().multicast().onBackpressureBuffer();
    private final Sinks.Many<AuditWithdrawal> withdrawalSink = Sinks.many().multicast().onBackpressureBuffer();

    public AuditService(AuditDepositRepository auditDepositRepository, AuditWithdrawalRepository auditWithdrawalRepository) {
        this.auditDepositRepository = auditDepositRepository;
        this.auditWithdrawalRepository = auditWithdrawalRepository;
    }

    // Registrar una auditoría de depósito
    public Mono<AuditDeposit> registerDeposit(String userId, Double initialBalance, Double depositAmount, Double finalBalance) {
        AuditDeposit audit = new AuditDeposit(userId, initialBalance, depositAmount, finalBalance, LocalDateTime.now());
        return auditDepositRepository.save(audit)
                .doOnNext(depositSink::tryEmitNext); // Emitir el evento en tiempo real
    }

    // Registrar una auditoría de retiro
    public Mono<AuditWithdrawal> registerWithdrawal(String userId, Double initialBalance, Double withdrawalAmount, String withdrawalType, Double finalBalance) {
        AuditWithdrawal audit = new AuditWithdrawal(userId, initialBalance, withdrawalAmount, withdrawalType, finalBalance, LocalDateTime.now());
        return auditWithdrawalRepository.save(audit)
                .doOnNext(withdrawalSink::tryEmitNext); // Emitir el evento en tiempo real
    }

    // Flujo de auditorías de depósitos en tiempo real
    public Flux<AuditDeposit> streamDeposits() {
        return depositSink.asFlux();
    }

    // Flujo de auditorías de retiros en tiempo real
    public Flux<AuditWithdrawal> streamWithdrawals() {
        return withdrawalSink.asFlux();
    }
}

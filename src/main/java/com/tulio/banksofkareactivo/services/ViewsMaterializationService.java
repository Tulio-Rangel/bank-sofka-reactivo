package com.tulio.banksofkareactivo.services;

import com.tulio.banksofkareactivo.models.*;
import com.tulio.banksofkareactivo.repositories.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Comparator;

@Service
public class ViewsMaterializationService {

    private final AuditTransactionRepository auditTransactionRepository;
    private final AuditTransactionViewRepository auditTransactionViewRepository;
    private final AuditAccountViewRepository auditAccountViewRepository;
    private final AuditAccountRepository auditAccountRepository;
    private final AuditUserRepository auditUserRepository;
    private final AuditUserViewsRepository auditUserViewsRepository;

    public ViewsMaterializationService(AuditTransactionRepository auditTransactionRepository,
                                       AuditTransactionViewRepository auditTransactionViewRepository, AuditAccountViewRepository auditAccountViewRepository, AuditAccountRepository auditAccountRepository, AuditUserRepository auditUserRepository, AuditUserViewsRepository auditUserViewsRepository) {
        this.auditTransactionRepository = auditTransactionRepository;
        this.auditTransactionViewRepository = auditTransactionViewRepository;
        this.auditAccountViewRepository = auditAccountViewRepository;
        this.auditAccountRepository = auditAccountRepository;
        this.auditUserRepository = auditUserRepository;
        this.auditUserViewsRepository = auditUserViewsRepository;
    }

    public Mono<Void> generateTransactionViews() {
        return auditTransactionRepository.findAll()
                .sort(Comparator.comparing(AuditTransaction::getDate)) // Ordenar cronológicamente
                .groupBy(AuditTransaction::getUserId)
                .flatMap(group -> {
                    final double[] currentBalance = {0}; // Mantiene el saldo en memoria por usuario

                    return group.concatMap(event -> { // Se usa concatMap para mantener el orden
                        double initialBalance = currentBalance[0];
                        double finalBalance = event.getTransactionType() == TransactionType.DEPOSIT
                                ? initialBalance + event.getAmount()
                                : initialBalance - event.getAmount();

                        currentBalance[0] = finalBalance; // Actualizar saldo para la siguiente transacción

                        AuditTransactionViews view = new AuditTransactionViews(
                                event.getUserId(),
                                event.getTransactionType(),
                                event.getAmount(),
                                initialBalance,
                                finalBalance,
                                event.getDate()
                        );

                        return auditTransactionViewRepository.save(view);
                    });
                })
                .then();
    }

    public Mono<Void> generateUserViews() {
        return auditUserRepository.findAll()
                .groupBy(AuditUser::getEmail)
                .flatMap(group -> group.collectList()
                        .map(events -> {
                            LocalDateTime registeredAt = events.stream()
                                    .filter(e -> e.getUserEventType() == UserEventType.REGISTER)
                                    .map(AuditUser::getDate)
                                    .findFirst()
                                    .orElse(null);

                            LocalDateTime lastLogin = events.stream()
                                    .filter(e -> e.getUserEventType() == UserEventType.LOGIN)
                                    .map(AuditUser::getDate)
                                    .max(LocalDateTime::compareTo)
                                    .orElse(null);

                            return new AuditUserViews(group.key(), registeredAt, lastLogin);
                        })
                        .flatMap(auditUserViewsRepository::save)
                )
                .then();
    }

    // 📌 Procesa los eventos y actualiza la vista de cuentas bancarias
    public Mono<Void> generateAccountViews() {
        return auditAccountRepository.findAll()
                .flatMap(event -> {
                    AuditAccountViews view = new AuditAccountViews(event.getUserId(), event.getDate());
                    return auditAccountViewRepository.save(view);
                })
                .then();
    }
}

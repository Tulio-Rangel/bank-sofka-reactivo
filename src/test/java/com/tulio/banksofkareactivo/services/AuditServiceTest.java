package com.tulio.banksofkareactivo.services;

import com.tulio.banksofkareactivo.models.AuditTransaction;
import com.tulio.banksofkareactivo.models.TransactionType;
import com.tulio.banksofkareactivo.repositories.AuditTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditServiceTest {

    @Mock
    private AuditTransactionRepository auditTransactionRepository;

    private AuditService auditService;

    @BeforeEach
    void setUp() {
        auditService = new AuditService(auditTransactionRepository);
    }

    @Test
    void registerDeposit_ShouldCreateAuditTransaction() {
        // Arrange
        String userId = "user123";
        Double initialBalance = 100.0;
        Double depositAmount = 50.0;
        Double finalBalance = 150.0;

        AuditTransaction expectedTransaction = new AuditTransaction();
        expectedTransaction.setUserId(userId);
        expectedTransaction.setInitialBalance(initialBalance);
        expectedTransaction.setAmount(depositAmount);
        expectedTransaction.setFinalBalance(finalBalance);
        expectedTransaction.setTransactionType(TransactionType.DEPOSIT);

        when(auditTransactionRepository.save(any(AuditTransaction.class)))
                .thenReturn(Mono.just(expectedTransaction));

        // Act & Assert
        StepVerifier.create(auditService.registerDeposit(userId, initialBalance, depositAmount, finalBalance))
                .expectNextMatches(transaction ->
                        transaction.getUserId().equals(userId) &&
                                transaction.getInitialBalance().equals(initialBalance) &&
                                transaction.getAmount().equals(depositAmount) &&
                                transaction.getFinalBalance().equals(finalBalance) &&
                                transaction.getTransactionType().equals(TransactionType.DEPOSIT)
                )
                .verifyComplete();
    }

    @Test
    void registerWithdrawal_ShouldCreateAuditTransaction() {
        // Arrange
        String userId = "user123";
        Double initialBalance = 150.0;
        Double withdrawalAmount = 50.0;
        Double finalBalance = 100.0;

        AuditTransaction expectedTransaction = new AuditTransaction();
        expectedTransaction.setUserId(userId);
        expectedTransaction.setInitialBalance(initialBalance);
        expectedTransaction.setAmount(withdrawalAmount);
        expectedTransaction.setFinalBalance(finalBalance);
        expectedTransaction.setTransactionType(TransactionType.WITHDRAWAL);

        when(auditTransactionRepository.save(any(AuditTransaction.class)))
                .thenReturn(Mono.just(expectedTransaction));

        // Act & Assert
        StepVerifier.create(auditService.registerWithdrawal(userId, initialBalance, withdrawalAmount, finalBalance))
                .expectNextMatches(transaction ->
                        transaction.getUserId().equals(userId) &&
                                transaction.getInitialBalance().equals(initialBalance) &&
                                transaction.getAmount().equals(withdrawalAmount) &&
                                transaction.getFinalBalance().equals(finalBalance) &&
                                transaction.getTransactionType().equals(TransactionType.WITHDRAWAL)
                )
                .verifyComplete();
    }

    @Test
    void streamTransactions_ShouldEmitTransactions() {
        // Arrange
        AuditTransaction transaction = new AuditTransaction();
        transaction.setUserId("user123");
        transaction.setInitialBalance(0.0);
        transaction.setAmount(100.0);
        transaction.setFinalBalance(100.0);
        transaction.setTransactionType(TransactionType.DEPOSIT);

        when(auditTransactionRepository.save(any(AuditTransaction.class)))
                .thenReturn(Mono.just(transaction));

        // Act
        Mono<AuditTransaction> depositMono = auditService.registerDeposit(
                transaction.getUserId(),
                transaction.getInitialBalance(),
                transaction.getAmount(),
                transaction.getFinalBalance()
        );

        // Assert
        StepVerifier.create(depositMono
                        .thenMany(auditService.streamTransactions().take(1))
                        .timeout(Duration.ofSeconds(5)))
                .expectNextMatches(t ->
                        t.getUserId().equals("user123") &&
                                t.getTransactionType().equals(TransactionType.DEPOSIT)
                )
                .verifyComplete();
    }
}


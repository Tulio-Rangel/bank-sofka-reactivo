package com.tulio.banksofkareactivo.controllers;

import com.tulio.banksofkareactivo.dtos.AuditTransactionRequest;
import com.tulio.banksofkareactivo.models.AuditTransaction;
import com.tulio.banksofkareactivo.services.AuditService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebFluxTest(AuditController.class)
class AuditControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private AuditService auditService;

    @Test
    void registerDeposit_ShouldCreateAuditTransaction() {
        // Arrange
        AuditTransactionRequest request = new AuditTransactionRequest();
        request.setUserId("user123");
        request.setInitialBalance(100.0);
        request.setAmount(50.0);
        request.setFinalBalance(150.0);

        AuditTransaction response = new AuditTransaction();
        response.setUserId(request.getUserId());
        response.setInitialBalance(request.getInitialBalance());
        response.setAmount(request.getAmount());
        response.setFinalBalance(request.getFinalBalance());
        response.setTransactionType("DEPOSIT");

        when(auditService.registerDeposit(
                anyString(), anyDouble(), anyDouble(), anyDouble()))
                .thenReturn(Mono.just(response));

        // Act & Assert
        webTestClient.post()
                .uri("/api/audit/deposits")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AuditTransaction.class)
                .value(transaction -> {
                    assert transaction.getUserId().equals(request.getUserId());
                    assert transaction.getTransactionType().equals("DEPOSIT");
                });
    }

    @Test
    void registerWithdrawal_ShouldCreateAuditTransaction() {
        // Arrange
        AuditTransactionRequest request = new AuditTransactionRequest();
        request.setUserId("user123");
        request.setInitialBalance(150.0);
        request.setAmount(50.0);
        request.setFinalBalance(100.0);

        AuditTransaction response = new AuditTransaction();
        response.setUserId(request.getUserId());
        response.setInitialBalance(request.getInitialBalance());
        response.setAmount(request.getAmount());
        response.setFinalBalance(request.getFinalBalance());
        response.setTransactionType("WITHDRAWAL");

        when(auditService.registerWithdrawal(
                anyString(), anyDouble(), anyDouble(), anyDouble()))
                .thenReturn(Mono.just(response));

        // Act & Assert
        webTestClient.post()
                .uri("/api/audit/withdrawals")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AuditTransaction.class)
                .value(transaction -> {
                    assert transaction.getUserId().equals(request.getUserId());
                    assert transaction.getTransactionType().equals("WITHDRAWAL");
                });
    }

    @Test
    void streamTransactions_ShouldStreamAuditTransactions() {
        // Arrange
        AuditTransaction transaction = new AuditTransaction();
        transaction.setUserId("user123");
        transaction.setAmount(100.0);
        transaction.setTransactionType("DEPOSIT");

        when(auditService.streamTransactions())
                .thenReturn(Flux.just(transaction));

        // Act & Assert
        webTestClient.get()
                .uri("/api/audit/transactions/stream")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM)
                .returnResult(AuditTransaction.class)
                .getResponseBody()
                .as(StepVerifier::create)
                .expectNextMatches(t ->
                        t.getUserId().equals("user123") &&
                                t.getTransactionType().equals("DEPOSIT")
                )
                .verifyComplete();
    }
}

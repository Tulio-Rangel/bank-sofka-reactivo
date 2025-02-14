package com.tulio.banksofkareactivo.controllers;

import com.tulio.banksofkareactivo.dtos.AuditTransactionRequest;
import com.tulio.banksofkareactivo.models.AuditTransaction;
import com.tulio.banksofkareactivo.models.TransactionType;
import com.tulio.banksofkareactivo.services.AuditService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebFluxTest(AuditController.class)
@Import(MockConfig.class)
class AuditControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private AuditService auditService;

    private static final String USER = "user123";

    @Test
    void streamTransactionsShouldStreamAuditTransactions() {
        // Arrange
        AuditTransaction transaction = new AuditTransaction();
        transaction.setUserId(USER);
        transaction.setAmount(100.0);
        transaction.setTransactionType(TransactionType.DEPOSIT);

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
                        t.getUserId().equals(USER) &&
                                t.getTransactionType().equals(TransactionType.DEPOSIT)
                )
                .verifyComplete();
    }
}

package com.tulio.banksofkareactivo.controllers;

import com.tulio.banksofkareactivo.dtos.AuditTransactionRequest;
import com.tulio.banksofkareactivo.models.AuditTransaction;
import com.tulio.banksofkareactivo.services.AuditService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/audit")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    // Endpoint para registrar un depósito
    @PostMapping("/deposits")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AuditTransaction> registerDeposit(@RequestBody AuditTransactionRequest request) {
        return auditService.registerDeposit(
                request.getUserId(),
                request.getInitialBalance(),
                request.getAmount(),
                request.getFinalBalance()
        );
    }

    // Endpoint para registrar un retiro
    @PostMapping("/withdrawals")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AuditTransaction> registerWithdrawal(@RequestBody AuditTransactionRequest request) {
        return auditService.registerWithdrawal(
                request.getUserId(),
                request.getInitialBalance(),
                request.getAmount(),
                request.getFinalBalance()
        );
    }

    // Endpoint para transmitir transacciones en tiempo real
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/transactions/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<AuditTransaction> streamTransactions() {
        return auditService.streamTransactions();
    }
}

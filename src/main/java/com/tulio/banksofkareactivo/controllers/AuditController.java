package com.tulio.banksofkareactivo.controllers;

import com.tulio.banksofkareactivo.dtos.AuditDepositRequest;
import com.tulio.banksofkareactivo.dtos.AuditWithdrawalRequest;
import com.tulio.banksofkareactivo.models.AuditDeposit;
import com.tulio.banksofkareactivo.models.AuditWithdrawal;
import com.tulio.banksofkareactivo.services.AuditService;
import org.springframework.http.HttpStatus;
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
    public Mono<AuditDeposit> registerDeposit(@RequestBody AuditDepositRequest request) {
        return auditService.registerDeposit(
                request.getUserId(),
                request.getInitialBalance(),
                request.getDepositAmount(),
                request.getFinalBalance()
        );
    }

    // Endpoint para registrar un retiro
    @PostMapping("/withdrawals")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AuditWithdrawal> registerWithdrawal(@RequestBody AuditWithdrawalRequest request) {
        return auditService.registerWithdrawal(
                request.getUserId(),
                request.getInitialBalance(),
                request.getWithdrawalAmount(),
                request.getWithdrawalType(),
                request.getFinalBalance()
        );
    }

    // Endpoint para transmitir depósitos en tiempo real
    @GetMapping(value = "/deposits/stream", produces = "application/stream+json")
    public Flux<AuditDeposit> streamDeposits() {
        return auditService.streamDeposits();
    }

    // Endpoint para transmitir retiros en tiempo real
    @GetMapping(value = "/withdrawals/stream", produces = "application/stream+json")
    public Flux<AuditWithdrawal> streamWithdrawals() {
        return auditService.streamWithdrawals();
    }
}

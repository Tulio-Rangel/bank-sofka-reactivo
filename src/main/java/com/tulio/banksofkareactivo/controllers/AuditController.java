package com.tulio.banksofkareactivo.controllers;

import com.tulio.banksofkareactivo.models.AuditTransaction;
import com.tulio.banksofkareactivo.services.AuditService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/audit")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    // Endpoint para transmitir transacciones en tiempo real
    @CrossOrigin(origins = {"http://localhost:4200", "http://banksofka-frontend:80", "http://banksofka-frontend:4200"})
    @GetMapping(value = "/transactions/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<AuditTransaction> streamTransactions() {
        return auditService.streamTransactions();
    }
}

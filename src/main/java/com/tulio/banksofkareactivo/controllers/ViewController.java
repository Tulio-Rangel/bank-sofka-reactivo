package com.tulio.banksofkareactivo.controllers;

import com.tulio.banksofkareactivo.models.AuditAccountViews;
import com.tulio.banksofkareactivo.models.AuditTransactionViews;
import com.tulio.banksofkareactivo.models.AuditUserViews;
import com.tulio.banksofkareactivo.repositories.AuditAccountViewRepository;
import com.tulio.banksofkareactivo.repositories.AuditTransactionViewRepository;
import com.tulio.banksofkareactivo.repositories.AuditUserViewsRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/views")
public class ViewController {

    private final AuditTransactionViewRepository auditTransactionViewRepository;
    private final AuditUserViewsRepository auditUserViewsRepository;
    private final AuditAccountViewRepository auditAccountViewRepository;

    public ViewController(AuditTransactionViewRepository auditTransactionViewRepository, AuditUserViewsRepository auditUserViewsRepository, AuditAccountViewRepository auditAccountViewRepository) {
        this.auditTransactionViewRepository = auditTransactionViewRepository;
        this.auditUserViewsRepository = auditUserViewsRepository;
        this.auditAccountViewRepository = auditAccountViewRepository;
    }

    @GetMapping("/transactions/{userId}")
    public Flux<AuditTransactionViews> getTransactionSummary(@PathVariable String userId) {
        return auditTransactionViewRepository.findByUserId(userId);
    }

    @GetMapping("/users/{userId}")
    public Mono<AuditUserViews> getUserSummary(@PathVariable String userId) {
        return auditUserViewsRepository.findById(userId);
    }

    @GetMapping("/accounts/{userId}")
    public Mono<AuditAccountViews> getAccountSummary(@PathVariable String userId) {
        return auditAccountViewRepository.findById(userId);
    }
}

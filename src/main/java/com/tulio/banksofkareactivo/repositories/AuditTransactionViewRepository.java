package com.tulio.banksofkareactivo.repositories;

import com.tulio.banksofkareactivo.models.AuditTransactionViews;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface AuditTransactionViewRepository extends ReactiveMongoRepository<AuditTransactionViews, String> {
    Flux<AuditTransactionViews> findByUserId(String userId);
}

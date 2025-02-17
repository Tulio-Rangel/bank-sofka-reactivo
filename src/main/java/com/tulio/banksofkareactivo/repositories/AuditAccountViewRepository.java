package com.tulio.banksofkareactivo.repositories;

import com.tulio.banksofkareactivo.models.AuditAccountViews;
import com.tulio.banksofkareactivo.models.AuditTransactionViews;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface AuditAccountViewRepository extends ReactiveMongoRepository<AuditAccountViews, String> {
    Flux<AuditAccountViews> findByUserId(String userId);
}

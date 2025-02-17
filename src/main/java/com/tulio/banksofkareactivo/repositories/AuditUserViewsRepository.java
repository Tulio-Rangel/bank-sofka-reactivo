package com.tulio.banksofkareactivo.repositories;

import com.tulio.banksofkareactivo.models.AuditUserViews;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface AuditUserViewsRepository extends ReactiveMongoRepository<AuditUserViews, String> {
    Flux<AuditUserViewsRepository> findByEmail(String email);
}

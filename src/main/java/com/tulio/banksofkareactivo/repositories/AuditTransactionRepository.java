package com.tulio.banksofkareactivo.repositories;

import com.tulio.banksofkareactivo.models.AuditTransaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditTransactionRepository extends ReactiveMongoRepository<AuditTransaction, String> {
}


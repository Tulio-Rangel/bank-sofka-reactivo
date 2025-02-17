package com.tulio.banksofkareactivo.repositories;

import com.tulio.banksofkareactivo.models.AuditAccounts;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditAccountRepository extends ReactiveMongoRepository<AuditAccounts, String> {
}

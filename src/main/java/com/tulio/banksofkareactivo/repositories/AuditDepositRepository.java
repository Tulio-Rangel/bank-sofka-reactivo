package com.tulio.banksofkareactivo.repositories;

import com.tulio.banksofkareactivo.models.AuditDeposit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditDepositRepository extends ReactiveMongoRepository<AuditDeposit, String> {
}

package com.tulio.banksofkareactivo.repositories;

import com.tulio.banksofkareactivo.models.AuditWithdrawal;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditWithdrawalRepository extends ReactiveMongoRepository<AuditWithdrawal, String> {
}

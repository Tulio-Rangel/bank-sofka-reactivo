package com.tulio.banksofkareactivo.repositories;

import com.tulio.banksofkareactivo.models.AuditUser;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditUserRepository extends ReactiveMongoRepository<AuditUser, String> {
}

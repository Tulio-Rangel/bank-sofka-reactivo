package com.tulio.banksofkareactivo.controllers;

import com.tulio.banksofkareactivo.services.AuditService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class MockConfig {

    @Bean
    @Primary
    public AuditService auditService() {
        return Mockito.mock(AuditService.class);
    }

}

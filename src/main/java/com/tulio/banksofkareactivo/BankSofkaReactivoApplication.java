package com.tulio.banksofkareactivo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class BankSofkaReactivoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankSofkaReactivoApplication.class, args);
    }

}

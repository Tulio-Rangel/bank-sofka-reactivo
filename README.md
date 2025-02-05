# Bank Reactive Audit Service

Este es un servicio de auditoría reactivo para transacciones bancarias construido con Spring WebFlux y MongoDB. El servicio registra y transmite en tiempo real las transacciones de depósitos y retiros.

## Características

- Registro de auditoría para depósitos y retiros
- Transmisión en tiempo real de transacciones usando Server-Sent Events (SSE)
- Documentación API con OpenAPI/Swagger
- Persistencia reactiva con MongoDB
- Arquitectura basada en eventos

## Requisitos Previos

- Java 17
- MongoDB
- Gradle

## Estructura del Proyecto

```
src/main/java/com/tulio/banksofkareactivo/
├── config/
│   └── OpenApiConfig.java
├── controllers/
│   └── AuditController.java
├── dtos/
│   └── AuditTransactionRequest.java
├── models/
│   └── AuditTransaction.java
├── repositories/
│   └── AuditTransactionRepository.java
└── services/
    └── AuditService.java
```

## API Endpoints

### POST /api/audit/deposits
Registra una transacción de depósito.

```json
{
  "userId": "string",
  "initialBalance": 0.0,
  "amount": 0.0,
  "finalBalance": 0.0
}
```

### POST /api/audit/withdrawals
Registra una transacción de retiro.

```json
{
  "userId": "string",
  "initialBalance": 0.0,
  "amount": 0.0,
  "finalBalance": 0.0
}
```

### GET /api/audit/transactions/stream
Transmite las transacciones en tiempo real usando Server-Sent Events (SSE).

## Modelo de Datos

La colección `audit_transactions` en MongoDB almacena los siguientes campos:

- `id`: Identificador único de la transacción
- `userId`: ID del usuario que realizó la transacción
- `initialBalance`: Saldo inicial antes de la transacción
- `amount`: Monto de la transacción
- `finalBalance`: Saldo final después de la transacción
- `transactionType`: Tipo de transacción ("DEPOSIT" o "WITHDRAWAL")
- `date`: Fecha y hora de la transacción

## Configuración

### application.properties
```properties
spring.data.mongodb.uri=mongodb://localhost:27017/bankaudit
```

## Documentación API

La documentación de la API está disponible a través de Swagger UI en:
```
http://localhost:8081/swagger-ui.html
```

## Características Técnicas

- Programación reactiva con Project Reactor
- Manejo de back pressure con Sinks.many().multicast().onBackpressureBuffer()
- Formato de fecha ISO 8601 para timestamps
- Respuestas HTTP apropiadas (201 para creación, etc.)

## Uso de Ejemplo

### Registrar un Depósito

```bash
curl -X POST http://localhost:8081/api/audit/deposits \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "123",
    "initialBalance": 1000.0,
    "amount": 500.0,
    "finalBalance": 1500.0
  }'
```

### Escuchar Transacciones en Tiempo Real

```bash
curl -N http://localhost:8081/api/audit/transactions/stream
```

## Seguridad y Mejores Prácticas

- Validación de entrada de datos
- Manejo reactivo de errores
- Formato consistente de respuestas JSON
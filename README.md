# Bank Reactive Audit Service

Este es un servicio de auditoría reactivo para transacciones bancarias construido con Spring WebFlux y MongoDB. El servicio registra y transmite en tiempo real las transacciones de depósitos y retiros.
Además cuenta con 
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

## CI/CD: Integración y Despliegue Continuo
Este repositorio incluye un flujo de CI/CD con las siguientes características:

### Construcción y Versionado con Docker y GHCR

- Se construye una imagen de Docker del servicio.

- Se almacena y versiona en GitHub Container Registry (GHCR).

- Se asegura la compatibilidad con Podman para ejecución sin daemon.

### Automatización con GitHub Actions y SonarCloud

- **Análisis de código con SonarCloud:** Se ejecuta un escaneo de calidad de código en cada commit.

- **Compilación y pruebas:** Se ejecutan tests automáticos antes de construir la imagen.

- **Construcción y publicación de imagen:** Se sube la imagen a GHCR.

### Despliegue con Podman
Podman permite ejecutar los contenedores sin necesidad de un daemon en ejecución, garantizando compatibilidad con Docker.

**Construcción del Containerfile**
```yaml
FROM gradle:jdk17-corretto-al2023 AS build
WORKDIR /app

COPY build.gradle.kts settings.gradle.kts /app/

COPY src /app/src

RUN gradle clean
RUN gradle build -x test

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

ARG DB_URL

ENV DB_URL=${DB_URL}

COPY --from=build /app/build/libs/*.jar /app/BankSofkaReactivo.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "BankSofkaReactivo.jar"]
```

**Pasos para ejecutar con Podman:**
```bash
   # Build de la imagen
   podman build -t bank-sofka-reactive .
   
   # Iniciar el contenedor
   podman run -p 8081:8081 bank-sofka-reactive
```
### Archivo del GitHub Actions
Ejemplo de ```.github/workflows/ci-cd.yml:```

```yml
name: Java CI/CD Pipeline

on:
  push:
    branches: [main]
    tags: ['v*.*.*']
  pull_request:
    branches: [main]

env:
  REGISTRY: ghcr.io
  IMAGE_NAME: ${{ github.repository }}
  GRADLE_VERSION: 8.7

jobs:
  test-and-analyze:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4

      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: 17
          distribution: 'temurin'

      - name: Run Tests with Gradle
        run: ./gradlew test

      - name: SonarCloud Analysis
        uses: sonarsource/sonarcloud-github-action@master
        env:
          GITHUB_TOKEN: ${{ secrets.GHCR_TOKEN }}
          SONAR_TOKEN: ${{ secrets.SONAR_TOKEN }}
        with:
          args: >
            -Dsonar.java.binaries=build/classes
            -Dsonar.gradle.skipCompile=true
            -Dsonar.projectKey=${{vars.PROJECT_KEY}}
            -Dsonar.organization=${{vars.ORGANIZATION}}

  build-and-push:
    runs-on: ubuntu-latest
    needs: test-and-analyze
    permissions:
      contents: read
      packages: write

    steps:
      - uses: actions/checkout@v4

      - name: Set up Gradle
        uses: gradle/gradle-build-action@v3
        with:
          gradle-version: ${{ env.GRADLE_VERSION }}

      - name: Log in to GHCR
        uses: docker/login-action@v3
        with:
          registry: ${{ env.REGISTRY }}
          username: ${{ github.actor }}
          password: ${{ secrets.GHCR_TOKEN }}

      - name: Build and Push Docker Image
        uses: docker/build-push-action@v5
        with:
          context: .
          file: Containerfile
          push: ${{ github.event_name != 'pull_request' }}
          tags: |
            ghcr.io/tulio-rangel/bank-sofka-reactive:latest
            ghcr.io/tulio-rangel/bank-sofka-reactive:${{ github.ref_name }}
          build-args: |
            DB_URL=${{ secrets.DB_URL }}
```

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


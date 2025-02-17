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
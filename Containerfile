FROM gradle:jdk17-corretto-al2023 AS build
WORKDIR /app

COPY build.gradle.kts settings.gradle.kts /app/

COPY src /app/src

RUN gradle clean
RUN gradle build -x test

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /app/build/libs/*.jar /app/BankSofkaReactivo.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "BankSofkaReactivo.jar"]
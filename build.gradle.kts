plugins {
    java
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.sonarqube") version "6.0.1.5171"
}

group = "com.tulio"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("io.projectreactor:reactor-core")
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.2.0")
    implementation("io.swagger.core.v3:swagger-annotations:2.2.21")
    implementation("org.springframework.boot:spring-boot-starter-activemq")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}


sonar {
    properties {
        property("sonar.projectKey", "jhonatanls_banksofkareactivo")
        property("sonar.organization", "jhonatanls")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}

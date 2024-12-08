plugins {
    kotlin("plugin.jpa") version "1.8.22"
    kotlin("plugin.noarg") version "2.0.21"
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

noArg {
    annotation("jakarta.persistence.Entity")
    annotation("com.grepp.quizy.common.NoArg")
}

dependencies {
    // Kafka Module
    api(project(":infrastructure:kafka"))
    // Game Domain Module
    implementation(project(":game-service:game-domain"))
    //open feign
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.1.3")
    implementation("org.springframework.cloud:spring-cloud-commons:4.1.4")
    // H2 Database
    runtimeOnly("com.h2database:h2")
    // MySQL
    runtimeOnly("com.mysql:mysql-connector-j")
    // JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    // Redis
    implementation("com.github.codemonstur:embedded-redis:1.4.3")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    // WebSocket
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    // Jackson
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    // Spring Configuration
    implementation("org.springframework.boot:spring-boot-configuration-processor")
}

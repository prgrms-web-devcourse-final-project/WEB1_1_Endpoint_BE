plugins {
    kotlin("plugin.jpa") version "1.8.22"
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

dependencies {
    // Kafka Module
    api(project(":infrastructure:kafka"))
    // Game Domain Module
    implementation(project(":game-service:game-domain"))
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
}

plugins {
    id("io.kotest.multiplatform") version "5.0.2"
    kotlin("plugin.jpa") version "1.8.22"
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

dependencies {
    api(project(":infrastructure:kafka"))

    implementation(project(":user-service:user-domain"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("mysql:mysql-connector-java:8.0.28")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
}
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
    implementation(project(":common:common-jpa"))

    implementation(project(":user-service:user-domain"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("mysql:mysql-connector-java:8.0.28")

    // Redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    // Feign
    //implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

    // H2 Database
    runtimeOnly("com.h2database:h2")

    // GCS
    implementation("org.springframework.cloud", "spring-cloud-gcp-starter", "1.2.5.RELEASE")
    implementation("org.springframework.cloud", "spring-cloud-gcp-storage", "1.2.5.RELEASE")
}


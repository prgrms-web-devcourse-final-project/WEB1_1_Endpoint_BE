plugins {
    kotlin("plugin.jpa") version "1.8.22"
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}


dependencies {
    // JPA
    implementation(project(":common"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}
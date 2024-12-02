import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile

plugins {
    kotlin("plugin.noarg") version "2.0.21"
}

noArg {
    annotation("com.grepp.quizy.common.NoArg")
}

dependencies {
    implementation(project(":common:common-web"))
    implementation(project(":quiz-service:quiz-domain"))
    implementation(project(":quiz-service:quiz-infra"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    //swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")

    // logback
    implementation("net.logstash.logback:logstash-logback-encoder:8.0")
}

tasks.named<BootJar>("bootJar") {
    enabled = true
}

tasks {
    withType<JavaCompile> {
        options.compilerArgs.add("-parameters")
    }
}
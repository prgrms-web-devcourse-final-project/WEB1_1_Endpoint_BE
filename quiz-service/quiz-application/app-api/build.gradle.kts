import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation(project(":quiz-service:quiz-domain"))
    implementation(project(":quiz-service:quiz-infra"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    //swagger
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.6.0")
}

tasks.named<BootJar>("bootJar") {
    enabled = true
}
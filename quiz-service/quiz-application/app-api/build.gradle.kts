import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {

    implementation(project(":quiz-service:quiz-domain"))
    implementation(project(":quiz-service:quiz-infra"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
}

tasks.named<BootJar>("bootJar") {
    enabled = true
}
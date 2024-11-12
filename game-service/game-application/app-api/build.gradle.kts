import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation(project(":game-service:game-domain"))
    implementation(project(":game-service:game-infra"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
}

tasks.named<BootJar>("bootJar") {
    enabled = true
}
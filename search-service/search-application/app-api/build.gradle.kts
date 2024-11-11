import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation(project(":search-service:search-domain"))
    implementation(project(":search-service:search-infra"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
}

tasks.named<BootJar>("bootJar") {
    enabled = true
}
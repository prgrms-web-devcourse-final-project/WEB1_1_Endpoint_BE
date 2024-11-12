import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation("org.springframework.cloud:spring-cloud-starter-gateway:4.1.5")
    implementation("org.springframework.cloud:spring-cloud-starter-kubernetes:1.1.10.RELEASE")

    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.6.0")
}

tasks.named<BootJar>("bootJar") {
    enabled = true
}
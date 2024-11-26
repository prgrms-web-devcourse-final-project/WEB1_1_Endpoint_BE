import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation(project(":common:common-web"))
    implementation(project(":matching-service:matching-domain"))
    implementation(project(":matching-service:matching-infra"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    //swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}

tasks.named<BootJar>("bootJar") {
    enabled = true
}

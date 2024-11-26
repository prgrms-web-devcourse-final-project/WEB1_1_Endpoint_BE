import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation(project(":common:common-web"))
    implementation(project(":user-service:user-domain"))
    implementation(project(":user-service:user-infra"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    //swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")

    // JWT
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    // Spring Security
    implementation("org.springframework.boot:spring-boot-starter-security")
    testImplementation("org.springframework.security:spring-security-test")

    // OAuth2
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    
}

tasks.named<BootJar>("bootJar") {
    enabled = true
}
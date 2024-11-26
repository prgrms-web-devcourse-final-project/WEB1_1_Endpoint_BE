dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}
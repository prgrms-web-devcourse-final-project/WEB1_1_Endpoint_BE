dependencies {
    implementation(project(":common"))
    implementation(project(":matching-service:matching-domain"))

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
}
dependencies {
    implementation(project(":common"))
    implementation(project(":matching-service:matching-domain"))

    implementation("org.springframework.boot:spring-boot-starter-data-redis")
}
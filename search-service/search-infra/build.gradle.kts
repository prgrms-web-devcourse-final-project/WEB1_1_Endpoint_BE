dependencies {
    implementation(project(":common"))
    implementation(project(":search-service:search-domain"))

    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.1.3")
    implementation("org.springframework.cloud:spring-cloud-commons:4.1.4")

    implementation("org.springframework.boot:spring-boot-starter-data-elasticsearch")
}

plugins {
    kotlin("plugin.noarg") version "2.0.21"
}

noArg {
    annotation("com.grepp.quizy.common.NoArg")
}

dependencies {
    api(project(":common"))
    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-tx")
}
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25" apply false
	id("org.springframework.boot") version "3.3.5" apply false
	id("io.spring.dependency-management") version "1.1.6" apply false
    kotlin("plugin.serialization") version "1.9.25" apply false
}

group = "com.grepp"
version = "0.0.1"

tasks.named<Jar>("jar") {
    enabled = true
}

allprojects {
    group = "com.grepp.quizy"
    version = "0.0.1"

    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = uri("https://packages.confluent.io/maven")
        }
    }
}


java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

subprojects {
	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "org.jetbrains.kotlin.plugin.spring")
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = uri("https://packages.confluent.io/maven")
        }
    }

	dependencies {
		implementation("org.springframework.boot:spring-boot-starter")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
		annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")

        // kotest
        testImplementation("io.kotest:kotest-runner-junit5:5.9.0")
        testImplementation("io.kotest:kotest-assertions-core:5.9.0")
        testImplementation("io.kotest.extensions:kotest-extensions-spring:1.3.0")
        // mockk
        testImplementation("io.mockk:mockk:1.13.13")

        //logging
        implementation("io.github.oshai:kotlin-logging-jvm:5.1.1")

        //serialization
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")

        // coroutines
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j")
    }

    tasks.named<BootJar>("bootJar") {
        enabled = false
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}







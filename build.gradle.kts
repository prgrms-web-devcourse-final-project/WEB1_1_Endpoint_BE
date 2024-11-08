import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25" apply false
	id("org.springframework.boot") version "3.3.5" apply false
	id("io.spring.dependency-management") version "1.1.6" apply false
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

	dependencies {
		implementation("org.springframework.boot:spring-boot-starter")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

		testImplementation("org.springframework.boot:spring-boot-starter-test")
		testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
		testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	}
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

project(":quiz-service") {
	tasks.named<BootJar>("bootJar") {
		enabled = false
	}

	subprojects {
		tasks.named<BootJar>("bootJar") {
			enabled = false
		}
	}
}

project(":user-service") {
	tasks.named<BootJar>("bootJar") {
		enabled = false
	}

	subprojects {
		tasks.named<BootJar>("bootJar") {
			enabled = false
		}
	}
}

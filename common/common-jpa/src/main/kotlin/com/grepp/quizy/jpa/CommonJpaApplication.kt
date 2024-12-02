package com.grepp.quizy.jpa

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories(basePackages = ["com.grepp.quizy"])
@SpringBootApplication(scanBasePackages = ["com.grepp.quizy"])
class CommonJpaApplication

fun main(args: Array<String>) {
}
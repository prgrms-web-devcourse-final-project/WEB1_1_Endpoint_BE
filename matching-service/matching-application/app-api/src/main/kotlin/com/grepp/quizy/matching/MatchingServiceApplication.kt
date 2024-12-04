package com.grepp.quizy.matching

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.grepp.quizy"])
class MatchingServiceApplication

fun main(args: Array<String>) {
    runApplication<MatchingServiceApplication>(*args)
}

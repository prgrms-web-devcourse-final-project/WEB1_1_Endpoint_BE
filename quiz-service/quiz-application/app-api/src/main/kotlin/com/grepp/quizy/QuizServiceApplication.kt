package com.grepp.quizy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class QuizServiceApplication {
}

fun main(args: Array<String>) {
    runApplication<QuizServiceApplication>(*args)
}
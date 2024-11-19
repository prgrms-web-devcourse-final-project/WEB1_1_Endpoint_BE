package com.grepp.quizy.quiz

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.grepp.quizy"])
class QuizServiceApplication {}

fun main(args: Array<String>) {
    runApplication<QuizServiceApplication>(*args)
}

package com.grepp.quizy.domain.quiz

interface QuizRepository {
    fun save(quiz: Quiz): Quiz
}

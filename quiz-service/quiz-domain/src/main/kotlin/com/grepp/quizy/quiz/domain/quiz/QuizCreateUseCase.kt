package com.grepp.quizy.quiz.domain.quiz

interface QuizCreateUseCase {
    fun create(type: QuizType, content: QuizContent, answer: QuizAnswer): Quiz
}

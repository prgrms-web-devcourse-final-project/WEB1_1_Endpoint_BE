package com.grepp.quizy.quiz.domain.quiz

import org.springframework.stereotype.Component

@Component
class QuizRemover(
        private val quizRepository: QuizRepository
) {
    fun remove(quiz: Quiz) {
        quizRepository.delete(quiz)
    }
}

package com.grepp.quizy.quiz.domain.quiz

import org.springframework.stereotype.Component

@Component
class QuizValidator(private val quizRepository: QuizRepository) {

    fun validateUpdatable(quiz: Quiz): Boolean {
        return quizRepository.existsUserAnswerByQuizId(quiz.id)
    }
}

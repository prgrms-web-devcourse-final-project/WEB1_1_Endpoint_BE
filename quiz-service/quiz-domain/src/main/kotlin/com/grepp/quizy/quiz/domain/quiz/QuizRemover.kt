package com.grepp.quizy.quiz.domain.quiz

import com.grepp.quizy.quiz.domain.user.UserId
import org.springframework.stereotype.Component

@Component
class QuizRemover(private val quizRepository: QuizRepository) {
    fun remove(quiz: Quiz, deleterId: UserId) {
        quiz.validateOwner(deleterId)
        quizRepository.delete(quiz)
    }
}

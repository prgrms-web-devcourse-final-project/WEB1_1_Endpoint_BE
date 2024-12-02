package com.grepp.quizy.quiz.domain.useranswer

import org.springframework.stereotype.Component

@Component
class UserAnswerUpdater(
    val userAnswerRepository: UserAnswerRepository,
) {

    fun update(userAnswer: UserAnswer) {
        userAnswerRepository.save(userAnswer)
    }
}
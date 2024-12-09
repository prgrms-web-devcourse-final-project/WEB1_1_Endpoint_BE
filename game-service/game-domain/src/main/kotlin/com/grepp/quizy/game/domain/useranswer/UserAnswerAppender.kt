package com.grepp.quizy.game.domain.useranswer

import org.springframework.stereotype.Component

@Component
class UserAnswerAppender(
    private val userAnswerRepository: UserAnswerRepository
) {

    fun append(userAnswer: UserAnswer): UserAnswer {
        return userAnswerRepository.save(userAnswer)
    }

}
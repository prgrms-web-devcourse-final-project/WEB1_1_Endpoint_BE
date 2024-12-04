package com.grepp.quizy.game.domain.useranswer

import org.springframework.stereotype.Component

@Component
class UserAnswerReader(
    private val userAnswerRepository: UserAnswerRepository
) {

    fun readAll(userId: Long, gameId: Long): List<UserAnswer> {
        return userAnswerRepository.findAllByGameIdAndUserId(gameId, userId)
    }

}
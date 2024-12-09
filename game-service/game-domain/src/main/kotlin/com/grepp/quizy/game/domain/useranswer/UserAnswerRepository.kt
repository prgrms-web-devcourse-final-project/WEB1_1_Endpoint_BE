package com.grepp.quizy.game.domain.useranswer

interface UserAnswerRepository {

    fun save(userAnswer: UserAnswer): UserAnswer

    fun findAllByGameIdAndUserId(gameId: Long, userId: Long): List<UserAnswer>

}
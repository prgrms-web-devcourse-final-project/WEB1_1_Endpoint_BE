package com.grepp.quizy.game.domain.useranswer

interface UserAnswerRepository {

    fun save(userAnswer: UserAnswer): Long?

}
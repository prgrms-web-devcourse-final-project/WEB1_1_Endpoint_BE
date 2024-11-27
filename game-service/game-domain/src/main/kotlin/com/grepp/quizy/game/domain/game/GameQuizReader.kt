package com.grepp.quizy.game.domain.game

import org.springframework.stereotype.Component

@Component
class GameQuizReader(
    private val gameQuizRepository: GameQuizRepository,
) {

    fun read(gameId: Long) =
        gameQuizRepository.findQuizzes(gameId)

}
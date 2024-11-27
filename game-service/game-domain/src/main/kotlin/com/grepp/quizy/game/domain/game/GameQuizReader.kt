package com.grepp.quizy.game.domain.game

import org.springframework.stereotype.Component

@Component
class GameQuizReader(
    private val gameRepository: GameRepository,
) {

    fun read(gameId: Long) =
        gameRepository.findQuizzes(gameId)

}
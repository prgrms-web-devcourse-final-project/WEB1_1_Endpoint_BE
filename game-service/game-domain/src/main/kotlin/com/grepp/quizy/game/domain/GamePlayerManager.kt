package com.grepp.quizy.game.domain

import org.springframework.stereotype.Component

@Component
class GamePlayerManager(
    private val gameRepository: GameRepository,
) {

    fun join(game: Game, userId: Long): Game {
        val currentGame = game.join(userId)
        return gameRepository.save(currentGame)
    }

    fun quit(game: Game, userId: Long): Game {
        val currentGame = game.quit(userId)
        return gameRepository.save(currentGame)
    }

}
package com.grepp.quizy.game.domain

import org.springframework.stereotype.Component

@Component
class GamePlayerManager(
    private val gameRepository: GameRepository,
) {

    fun join(game: Game, userId: Long): Game {
        game.join(userId)
        return gameRepository.save(game)
    }

    fun quit(game: Game, userId: Long): Game {
        game.quit(userId)
        return gameRepository.save(game)
    }

}
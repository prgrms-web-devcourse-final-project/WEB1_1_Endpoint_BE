package com.grepp.quizy.game.domain

import org.springframework.stereotype.Component

@Component
class GameManager(
    private val gameRepository: GameRepository,
) {

    fun join(game: Game, userId: Long): Game {
        game.join(userId)
        gameRepository.save(game)
        return game
    }

    fun quit(game: Game, userId: Long): Game {
        game.quit(userId)
        gameRepository.save(game)
        return game
    }

}
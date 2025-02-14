package com.grepp.quizy.game.domain.game

import org.springframework.stereotype.Component

@Component
class GameMatchingManager(
    private val gameRepository: GameRepository
) {

    fun join(userId: Long, game: Game): Game {
        game.joinRandomGame(userId)
        return gameRepository.save(game)
    }

}
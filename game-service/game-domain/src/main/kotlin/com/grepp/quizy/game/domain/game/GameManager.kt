package com.grepp.quizy.game.domain.game

import org.springframework.stereotype.Component

@Component
class GameManager(
    private val gameRepository: GameRepository
) {
    fun destroy(game: Game) {
        gameRepository.delete(game)
    }
}
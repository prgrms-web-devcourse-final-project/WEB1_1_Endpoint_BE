package com.grepp.quizy.game.domain.game

import org.springframework.stereotype.Component

@Component
class GamePlayerManager(private val gameRepository: GameRepository) {

    fun join(game: Game, userId: Long): Game {
        game.join(userId)
        return gameRepository.save(game)
    }

    fun quit(game: Game, userId: Long): Game {
        game.quit(userId)
        return gameRepository.save(game)
    }

    fun kick(game: Game, userId: Long, targetUserId: Long): Game {
        game.kick(userId, targetUserId)
        return gameRepository.save(game)
    }
}

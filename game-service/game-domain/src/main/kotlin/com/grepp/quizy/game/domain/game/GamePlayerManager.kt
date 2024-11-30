package com.grepp.quizy.game.domain.game

import com.grepp.quizy.game.domain.user.User
import org.springframework.stereotype.Component

@Component
class GamePlayerManager(
    private val gameRepository: GameRepository
) {

    fun join(game: Game, user: User): Game {
        game.join(user)
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

package com.grepp.quizy.game.domain.game

import com.grepp.quizy.game.domain.user.User
import org.springframework.stereotype.Component

@Component
class GamePlayerManager(private val gameRepository: GameRepository) {

    fun join(game: Game, user: User): Game {
        game.join(user)
        return gameRepository.save(game)
    }

    fun quit(game: Game, user: User): Game {
        game.quit(user)
        return gameRepository.save(game)
    }

    fun kick(game: Game, user: User, targetUser: User): Game {
        game.kick(user, targetUser)
        return gameRepository.save(game)
    }
}

package com.grepp.quizy.game.domain.game

import com.grepp.quizy.game.domain.user.User
import org.springframework.stereotype.Component

@Component
class GameAppender(
    private val gameRepository: GameRepository,
    private val idGenerator: IdGenerator,
) {

    fun append(
        user: User,
        subject: GameSubject,
        level: GameLevel,
        quizCount: Int,
    ): Game {
        val game =
            Game.create(
                id = idGenerator.generate("game"),
                subject = subject,
                quizCount = quizCount,
                level = level,
                user = user,
            )
        return gameRepository.save(game)
    }

    fun appendRandomGame(
        users: List<User>,
        subject: GameSubject
    ): Game {
        val game = Game.random(
            id = idGenerator.generate("game"),
            subject = subject,
            users = users
        )
        return gameRepository.save(game)
    }
}

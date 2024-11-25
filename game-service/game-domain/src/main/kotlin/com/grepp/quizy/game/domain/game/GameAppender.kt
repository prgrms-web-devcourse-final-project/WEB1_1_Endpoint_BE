package com.grepp.quizy.game.domain.game

import org.springframework.stereotype.Component

@Component
class GameAppender(
    private val gameRepository: GameRepository,
    private val idGenerator: IdGenerator,
) {

    fun append(
        userId: Long,
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
                userId = userId,
            )
        return gameRepository.save(game)
    }

    fun appendRandomGame(
        userIds: List<Long>,
        subject: GameSubject
    ): Game {
        val game = Game.random(
            id = idGenerator.generate("game"),
            subject = subject,
            userIds = userIds
        )
        return gameRepository.save(game)
    }
}

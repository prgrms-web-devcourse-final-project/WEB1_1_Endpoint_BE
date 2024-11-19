package com.grepp.quizy.game.domain

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GameService(
    private val gameAppender: GameAppender,
    private val gameReader: GameReader,
    private val gameManager: GameManager,
    private val messagePublisher: GameMessagePublisher
) {

    @Transactional
    fun create(
        userId: Long,
        subject: GameSubject,
        level: GameLevel,
        quizCount: Int
    ): Game {
        return gameAppender.append(
            userId = userId,
            subject = subject,
            level = level,
            quizCount = quizCount
        )
    }

    @Transactional
    fun join(gameId: Long, userId: Long): Game {
        val game = gameReader.read(gameId)
        val currentGame = gameManager.join(game, userId)
        // TODO: publish message
        return currentGame
    }

    @Transactional
    fun quit(gameId: Long, userId: Long): Game {
        val game = gameReader.read(gameId)
        val currentGame = gameManager.quit(game, userId)
        // TODO: publish message
        return currentGame
    }

}
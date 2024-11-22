package com.grepp.quizy.game.domain

import org.springframework.stereotype.Service

@Service
class GameService(
        private val gameAppender: GameAppender,
        private val gameReader: GameReader,
        private val gamePlayerManager: GamePlayerManager,
        private val gameSettingManager: GameSettingManager,
        private val messagePublisher: GameMessagePublisher,
) {

    fun create(
            userId: Long,
            subject: GameSubject,
            level: GameLevel,
            quizCount: Int,
    ): Game {
        return gameAppender.append(
                userId = userId,
                subject = subject,
                level = level,
                quizCount = quizCount,
        )
    }

    fun join(userId: Long, code: String): Game {
        val game = gameReader.readByInviteCode(code)
        val currentGame = gamePlayerManager.join(game, userId)
        messagePublisher.publish(
                GameMessage.room(
                        currentGame.id,
                        GamePayload.from(currentGame),
                )
        )
        return currentGame
    }

    fun quit(userId: Long, gameId: Long) {
        val game = gameReader.read(gameId)
        val currentGame = gamePlayerManager.quit(game, userId)
        messagePublisher.publish(
                GameMessage.room(
                        currentGame.id,
                        GamePayload.from(currentGame),
                )
        )
    }

    fun updateSubject(
            userId: Long,
            gameId: Long,
            subject: GameSubject,
    ) {
        val game = gameReader.read(gameId)
        val currentGame =
                gameSettingManager.updateSubject(
                        game,
                        subject,
                        userId,
                )
        messagePublisher.publish(
                GameMessage.room(
                        currentGame.id,
                        GamePayload.from(currentGame),
                )
        )
    }

    fun updateLevel(userId: Long, gameId: Long, level: GameLevel) {
        val game = gameReader.read(gameId)
        val currentGame =
                gameSettingManager.updateLevel(game, level, userId)
        messagePublisher.publish(
                GameMessage.room(
                        currentGame.id,
                        GamePayload.from(currentGame),
                )
        )
    }

    fun updateQuizCount(userId: Long, gameId: Long, quizCount: Int) {
        val game = gameReader.read(gameId)
        val currentGame = gameSettingManager.updateQuizCount(game, quizCount, userId)
        messagePublisher.publish(
            GameMessage.room(
                currentGame.id,
                GamePayload.from(currentGame)
            )
        )
    }

    fun kickUser(userId: Long, gameId: Long, targetUserId: Long) {
        val game = gameReader.read(gameId)
        val currentGame =
                gamePlayerManager.kick(game, userId, targetUserId)
        messagePublisher.publish(
                GameMessage.room(
                        currentGame.id,
                        GamePayload.from(currentGame),
                )
        )
    }
}

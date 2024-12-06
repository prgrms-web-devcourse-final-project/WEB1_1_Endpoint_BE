package com.grepp.quizy.game.domain.game

import com.grepp.quizy.game.domain.GameMessage
import com.grepp.quizy.game.domain.message.MessagePublisher
import com.grepp.quizy.game.domain.message.StreamMessage
import com.grepp.quizy.game.domain.user.UserReader
import org.springframework.stereotype.Service

@Service
class GamePrivateService(
    private val gameAppender: GameAppender,
    private val gameReader: GameReader,
    private val gamePlayerManager: GamePlayerManager,
    private val gameSettingManager: GameSettingManager,
    private val userReader: UserReader,
    private val gameMessagePublisher: GameMessagePublisher,
    private val messagePublisher: MessagePublisher,
    private val messageSender: GameMessageSender
) {

    fun create(
        userId: Long,
        subject: String,
        level: String,
        quizCount: Int,
    ): Game {
        val user = userReader.read(userId)
        return gameAppender.append(
            user = user,
            subject = subject,
            level = level,
            quizCount = quizCount,
        )
    }

    fun join(userId: Long, code: String): Game {
        val game = gameReader.readByInviteCode(code)
        val user = userReader.read(userId)
        val currentGame = gamePlayerManager.join(game, user)
        gameMessagePublisher.publish(GameMessage.room(game))
        return currentGame
    }

    fun quit(userId: Long, gameId: Long) {
        val game = gameReader.read(gameId)
        val currentGame = gamePlayerManager.quit(game, userId)
        gameMessagePublisher.publish(GameMessage.room(currentGame))
    }

    fun updateSubject(
        userId: Long,
        gameId: Long,
        subject: String,
    ) {
        val game = gameReader.read(gameId)
        val currentGame =
            gameSettingManager.updateSubject(
                game,
                subject,
                userId,
            )
        gameMessagePublisher.publish(
            GameMessage.room(currentGame)
        )
    }

    fun updateLevel(userId: Long, gameId: Long, level: String) {
        val game = gameReader.read(gameId)
        val currentGame = gameSettingManager.updateLevel(game, level, userId)
        gameMessagePublisher.publish(
            GameMessage.room(currentGame)
        )
    }

    fun updateQuizCount(userId: Long, gameId: Long, quizCount: Int) {
        val game = gameReader.read(gameId)
        val currentGame = gameSettingManager.updateQuizCount(game, quizCount, userId)
        gameMessagePublisher.publish(
            GameMessage.room(currentGame)
        )
    }

    fun kickUser(userId: Long, gameId: Long, targetUserId: Long) {
        val game = gameReader.read(gameId)
        val currentGame = gamePlayerManager.kick(game, userId, targetUserId)
        gameMessagePublisher.publish(
            GameMessage.room(currentGame)
        )
        messageSender.send(
            targetUserId.toString(),
            GameMessage.kick(
                gameId,
                targetUserId
            )
        )
    }

    fun start(userId: Long, gameId: Long) {
        val game = gameReader.read(gameId)
        val startedGame = gameSettingManager.gameStart(game, userId)
        messagePublisher.publish(
            StreamMessage.gameStart(
                mapOf(
                    "gameId" to gameId.toString()
                )
            )
        )
    }
}

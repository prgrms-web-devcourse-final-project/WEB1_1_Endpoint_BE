package com.grepp.quizy.game.domain.game

import com.grepp.quizy.game.domain.GameMessage
import com.grepp.quizy.game.domain.RoomPayload
import com.grepp.quizy.game.domain.user.UserReader
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class GamePrivateService(
    private val gameAppender: GameAppender,
    private val gameReader: GameReader,
    private val gamePlayerManager: GamePlayerManager,
    private val gameSettingManager: GameSettingManager,
    private val userReader: UserReader,
    private val eventPublisher: ApplicationEventPublisher,
    private val messagePublisher: GameMessagePublisher,
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
        messagePublisher.publish(GameMessage.room(game))
        return currentGame
    }

    fun quit(userId: Long, gameId: Long) {
        val game = gameReader.read(gameId)
        val currentGame = gamePlayerManager.quit(game, userId)
        messagePublisher.publish(GameMessage.room(currentGame))
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
        messagePublisher.publish(
            GameMessage.room(currentGame)
        )
    }

    fun updateLevel(userId: Long, gameId: Long, level: String) {
        val game = gameReader.read(gameId)
        val currentGame = gameSettingManager.updateLevel(game, level, userId)
        messagePublisher.publish(
            GameMessage.room(currentGame)
        )
    }

    fun updateQuizCount(userId: Long, gameId: Long, quizCount: Int) {
        val game = gameReader.read(gameId)
        val currentGame = gameSettingManager.updateQuizCount(game, quizCount, userId)
        messagePublisher.publish(
            GameMessage.room(currentGame)
        )
    }

    fun kickUser(userId: Long, gameId: Long, targetUserId: Long) {
        val game = gameReader.read(gameId)
        val currentGame = gamePlayerManager.kick(game, userId, targetUserId)
        messagePublisher.publish(
            GameMessage.room(currentGame)
        )
    }

    fun start(userId: Long, gameId: Long) {
        val game = gameReader.read(gameId)
        val startedGame = gameSettingManager.gameStart(game, userId)
        eventPublisher.publishEvent(GameStartEvent(startedGame))
    }
}

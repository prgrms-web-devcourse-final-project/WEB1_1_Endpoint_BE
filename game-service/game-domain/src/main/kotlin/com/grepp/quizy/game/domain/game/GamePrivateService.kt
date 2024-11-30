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
        subject: GameSubject,
        level: GameLevel,
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
        messagePublisher.publish(
            GameMessage.room(
                currentGame.id,
                RoomPayload.from(currentGame),
            )
        )
        return currentGame
    }

    fun quit(userId: Long, gameId: Long) {
        val game = gameReader.read(gameId)
        val user = userReader.read(userId)
        val currentGame = gamePlayerManager.quit(game, user)
        messagePublisher.publish(
            GameMessage.room(
                currentGame.id,
                RoomPayload.from(currentGame),
            )
        )
    }

    fun updateSubject(
        userId: Long,
        gameId: Long,
        subject: GameSubject,
    ) {
        val game = gameReader.read(gameId)
        val user = userReader.read(userId)
        val currentGame =
            gameSettingManager.updateSubject(
                game,
                subject,
                user,
            )
        messagePublisher.publish(
            GameMessage.room(
                currentGame.id,
                RoomPayload.from(currentGame),
            )
        )
    }

    fun updateLevel(userId: Long, gameId: Long, level: GameLevel) {
        val game = gameReader.read(gameId)
        val user = userReader.read(userId)
        val currentGame =
            gameSettingManager.updateLevel(game, level, user)
        messagePublisher.publish(
            GameMessage.room(
                currentGame.id,
                RoomPayload.from(currentGame),
            )
        )
    }

    fun updateQuizCount(userId: Long, gameId: Long, quizCount: Int) {
        val game = gameReader.read(gameId)
        val user = userReader.read(userId)
        val currentGame = gameSettingManager.updateQuizCount(game, quizCount, user)
        messagePublisher.publish(
            GameMessage.room(
                currentGame.id,
                RoomPayload.from(currentGame)
            )
        )
    }

    fun kickUser(userId: Long, gameId: Long, targetUserId: Long) {
        val game = gameReader.read(gameId)
        val user = userReader.read(userId)
        val targetUser = userReader.read(targetUserId)
        val currentGame =
            gamePlayerManager.kick(game, user, targetUser)
        messagePublisher.publish(
            GameMessage.room(
                currentGame.id,
                RoomPayload.from(currentGame),
            )
        )
    }

    fun start(userId: Long, gameId: Long) {
        val game = gameReader.read(gameId)
        val startedGame = gameSettingManager.gameStart(game, userId)
        eventPublisher.publishEvent(GameStartEvent(startedGame))
    }
}

package com.grepp.quizy.game.domain.game

import com.grepp.quizy.game.domain.GameMessage
import com.grepp.quizy.game.domain.RoomPayload
import com.grepp.quizy.game.domain.user.UserReader
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class GameMatchingService(
    private val gameAppender: GameAppender,
    private val gameReader: GameReader,
    private val userReader: UserReader,
    private val gameMatchingManager: GameMatchingManager,
    private val messagePublisher: GameMessagePublisher,
    private val eventPublisher: ApplicationEventPublisher
) {

    fun create(userIds: List<Long>, subject: GameSubject): Game {
        userReader.readIn(userIds).let { users ->
            return gameAppender.appendRandomGame(
                users = users,
                subject = subject
            )
        }
    }

    fun join(userId: Long, gameId: Long) {
        val game = gameReader.read(gameId)
        gameMatchingManager.join(userId, game)
        messagePublisher.publish(
            GameMessage.room(game)
        )
        if (game.isReady()) {
            eventPublisher.publishEvent(GameStartEvent(game))
        }
    }

}
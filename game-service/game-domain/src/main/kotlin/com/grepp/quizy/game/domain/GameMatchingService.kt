package com.grepp.quizy.game.domain

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class GameMatchingService(
    private val gameAppender: GameAppender,
    private val gameReader: GameReader,
    private val messagePublisher: GameMessagePublisher,
    private val eventPublisher: ApplicationEventPublisher
) {

    fun create(userIds: List<Long>, subject: GameSubject): Game {
        return gameAppender.appendRandomGame(
            userIds = userIds,
            subject = subject
        )
    }

    fun join(userId: Long, gameId: Long) {
        val game = gameReader.read(gameId)
        game.joinRandomGame(userId)
        messagePublisher.publish(
            GameMessage.room(
                game.id,
                RoomPayload.from(game),
            )
        )
        if (game.isReady()) {
            eventPublisher.publishEvent(GameStartEvent(game))
        }
    }

}
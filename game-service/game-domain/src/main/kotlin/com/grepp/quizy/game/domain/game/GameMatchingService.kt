package com.grepp.quizy.game.domain.game

import com.grepp.quizy.game.domain.GameMessage
import com.grepp.quizy.game.domain.message.MessagePublisher
import com.grepp.quizy.game.domain.message.StreamMessage
import com.grepp.quizy.game.domain.user.UserReader
import org.springframework.stereotype.Service

@Service
class GameMatchingService(
    private val gameAppender: GameAppender,
    private val gameReader: GameReader,
    private val userReader: UserReader,
    private val gameMessagePublisher: GameMessagePublisher,
    private val messagePublisher: MessagePublisher,
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
        game.joinRandomGame(userId)
        gameMessagePublisher.publish(
            GameMessage.room(game)
        )
        if (game.isReady()) {
            messagePublisher.publish(
                StreamMessage.gameStart(
                    mapOf(
                        "gameId" to game.id.toString()
                    )
                )
            )
        }
    }

}
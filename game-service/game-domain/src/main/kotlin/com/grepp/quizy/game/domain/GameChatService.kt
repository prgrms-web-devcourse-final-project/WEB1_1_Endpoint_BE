package com.grepp.quizy.game.domain

import org.springframework.stereotype.Service

@Service
class GameChatService(
    private val messagePublisher: GameMessagePublisher
) {

    fun publishChat(userId: Long, gameId: Long, message: String) {
        messagePublisher.publish(
            GameMessage.chat(
                gameId,
                ChatPayload.from(
                    userId,
                    message
                )
            )
        )
    }

}
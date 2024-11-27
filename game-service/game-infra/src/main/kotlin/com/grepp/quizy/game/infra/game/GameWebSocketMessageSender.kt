package com.grepp.quizy.game.infra.game

import com.grepp.quizy.game.domain.GameMessage
import com.grepp.quizy.game.domain.game.GameMessageSender
import com.grepp.quizy.game.infra.websocket.WebSocketDestination.QUIZ_GRADE
import com.grepp.quizy.game.infra.websocket.WebSocketDestination.SINGLE_PREFIX
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component

@Component
class GameWebSocketMessageSender(
    private val messageTemplate: SimpMessagingTemplate
) : GameMessageSender {

    override fun send(userId: String, message: GameMessage) {
        messageTemplate.convertAndSendToUser(
            userId,
            "${SINGLE_PREFIX.destination}${QUIZ_GRADE.destination}",
            message,
        )
    }
}

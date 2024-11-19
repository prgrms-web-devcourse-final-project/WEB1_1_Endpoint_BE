package com.grepp.quizy.game.infra.websocket

import com.grepp.quizy.domain.game.GameMessageSender
import com.grepp.quizy.game.infra.websocket.WebSocketDestination.QUIZ_GRADE
import com.grepp.quizy.game.infra.websocket.WebSocketDestination.SINGLE_PREFIX
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component
import java.security.Principal

@Component
class GameWebSocketMessageSender(
    private val messageTemplate: SimpMessagingTemplate
) : GameMessageSender {

    override fun send(principal: Principal, message: String) {
        messageTemplate.convertAndSendToUser(
            principal.name,
            "${SINGLE_PREFIX.destination}${QUIZ_GRADE.destination}",
            message
        )
    }

}
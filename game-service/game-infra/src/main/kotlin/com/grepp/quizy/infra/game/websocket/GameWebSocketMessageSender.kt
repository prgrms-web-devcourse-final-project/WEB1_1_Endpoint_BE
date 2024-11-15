package com.grepp.quizy.infra.game.websocket

import com.grepp.quizy.domain.game.GameMessageSender
import com.grepp.quizy.infra.game.websocket.WebSocketDestination.*
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
            "${USER_PREFIX.destination}${QUIZ_GRADE.destination}",
            message
        )
    }

}
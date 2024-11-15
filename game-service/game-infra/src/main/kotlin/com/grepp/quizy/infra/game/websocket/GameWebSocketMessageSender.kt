package com.grepp.quizy.infra.game.websocket

import com.grepp.quizy.domain.game.GameMessageSender
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
            "/queue/quiz-grade",
            message
        )
    }

}
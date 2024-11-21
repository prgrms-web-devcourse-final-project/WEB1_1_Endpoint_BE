package com.grepp.quizy.game.infra.redis

import com.fasterxml.jackson.databind.ObjectMapper
import com.grepp.quizy.game.domain.GameMessage
import com.grepp.quizy.game.infra.websocket.WebSocketDestination.MULTIPLE_PREFIX
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component

@Component
class RedisSubscriber(
        private val objectMapper: ObjectMapper,
        private val messagingTemplate: SimpMessagingTemplate,
) : MessageListener {

    override fun onMessage(message: Message, pattern: ByteArray?) {
        val publishMessage = String(message.body)
        val gameMessage =
                objectMapper.readValue(
                        publishMessage,
                        GameMessage::class.java,
                )
        messagingTemplate.convertAndSend(
                "$MULTIPLE_PREFIX/game/${gameMessage.gameId}",
                publishMessage,
        )
    }
}

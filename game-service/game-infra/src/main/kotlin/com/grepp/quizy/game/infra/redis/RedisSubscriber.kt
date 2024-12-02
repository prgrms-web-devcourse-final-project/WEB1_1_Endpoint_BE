package com.grepp.quizy.game.infra.redis

import com.fasterxml.jackson.databind.ObjectMapper
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
        val publishMessage = message.toString()
        val gameId = objectMapper.readTree(publishMessage).get("gameId").asText()


        messagingTemplate.convertAndSend(
            "${MULTIPLE_PREFIX.destination}/game/${gameId}",
            publishMessage,
        )
    }
}

package com.grepp.quizy.game.infra.redis

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.grepp.quizy.game.infra.websocket.WebSocketDestination.MULTIPLE_PREFIX
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component

@Component
class RedisSubscriber(
    private val objectMapper: ObjectMapper,
    private val messagingTemplate: SimpMessagingTemplate
) : MessageListener {

    override fun onMessage(message: Message, pattern: ByteArray?) {
        val publishMessage = String(message.body)
        // TODO: 메시지 형식 수정 되면 수정 필요 현재는 객체가 아닌 json문자열 중 ID를 추출하여 사용
        val jsonNode: JsonNode = objectMapper.readTree(publishMessage)
        val roomId: String = jsonNode.get("roomId").asText()
        messagingTemplate.convertAndSend(
            "$MULTIPLE_PREFIX/gameRoom/$roomId", publishMessage
        )
    }

}
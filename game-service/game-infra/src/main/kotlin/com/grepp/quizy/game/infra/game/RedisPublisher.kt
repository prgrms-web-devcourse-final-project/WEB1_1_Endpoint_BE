package com.grepp.quizy.game.infra.game

import com.fasterxml.jackson.databind.ObjectMapper
import com.grepp.quizy.game.domain.GameMessage
import com.grepp.quizy.game.domain.game.GameMessagePublisher
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Component

@Component
class RedisPublisher(
    private val redisTemplate: RedisTemplate<String, String>,
    private val objectMapper: ObjectMapper,
    private val gameTopic: ChannelTopic,
) : GameMessagePublisher {
    override fun publish(message: GameMessage) {
        val messageJson = objectMapper.writeValueAsString(message)
        redisTemplate.convertAndSend(gameTopic.topic, messageJson)
    }
}

package com.grepp.quizy.game.infra.redis

import com.fasterxml.jackson.databind.ObjectMapper
import com.grepp.quizy.game.domain.GameMessagePublisher
import com.grepp.quizy.game.domain.Message
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Component

@Component
class RedisPublisher(
    private val redisTemplate: RedisTemplate<String, String>,
    private val objectMapper: ObjectMapper,
    private val gameTopic: ChannelTopic,
) : GameMessagePublisher {

    override fun publish(message: Message) {
        val messageJson = objectMapper.writeValueAsString(message)
        redisTemplate.convertAndSend(gameTopic.topic, messageJson)
    }

}
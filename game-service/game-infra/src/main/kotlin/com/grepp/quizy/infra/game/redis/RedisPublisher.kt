package com.grepp.quizy.infra.game.redis

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic

class RedisPublisher(
    private val redisTemplate: RedisTemplate<String, String>,
    private val objectMapper: ObjectMapper,
    private val gameTopic: ChannelTopic,
) {

    fun publish(message: String) {
        // TODO: 메시지 형식 지정 되면 수정
        val messageJson = objectMapper.writeValueAsString(message)
        redisTemplate.convertAndSend(gameTopic.topic, messageJson)
    }
}
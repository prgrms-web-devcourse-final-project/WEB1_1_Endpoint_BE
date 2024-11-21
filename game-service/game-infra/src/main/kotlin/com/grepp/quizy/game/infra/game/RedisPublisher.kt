package com.grepp.quizy.game.infra.game

import com.fasterxml.jackson.databind.ObjectMapper
import com.grepp.quizy.game.domain.GameMessage
import com.grepp.quizy.game.domain.GameMessagePublisher
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Component

@Component
class RedisPublisher(
<<<<<<< Updated upstream:game-service/game-infra/src/main/kotlin/com/grepp/quizy/game/infra/game/RedisPublisher.kt
    private val redisTemplate: RedisTemplate<String, String>,
    private val objectMapper: ObjectMapper,
    private val gameTopic: ChannelTopic,
) : GameMessagePublisher {
=======
        private val redisTemplate: RedisTemplate<String, String>,
        private val objectMapper: ObjectMapper,
        private val gameTopic: ChannelTopic,
) {
>>>>>>> Stashed changes:game-service/game-infra/src/main/kotlin/com/grepp/quizy/game/infra/redis/RedisPublisher.kt

    override fun publish(message: GameMessage) {
        val messageJson = objectMapper.writeValueAsString(message)
        redisTemplate.convertAndSend(gameTopic.topic, messageJson)
    }
}

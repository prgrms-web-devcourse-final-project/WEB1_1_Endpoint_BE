package com.grepp.quizy.game.infra.redis

import com.grepp.quizy.game.domain.message.MessagePublisher
import com.grepp.quizy.game.domain.message.StreamMessage
import com.grepp.quizy.game.infra.redis.util.RedisOperator
import org.springframework.stereotype.Component

@Component
class RedisStreamPublisher(
    private val redisOperator: RedisOperator
) : MessagePublisher {
    override fun publish(message: StreamMessage) {
        redisOperator.sendStreamMessage(message.streamKey, message)
    }
}
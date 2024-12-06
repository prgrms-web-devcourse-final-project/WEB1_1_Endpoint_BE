package com.grepp.quizy.game.infra.redis

import com.grepp.quizy.game.domain.user.UserUpdater
import com.grepp.quizy.game.infra.redis.util.RedisOperator
import jakarta.annotation.PostConstruct
import org.springframework.data.redis.connection.stream.MapRecord
import org.springframework.stereotype.Component

@Component
class RatingStreamConsumer(
    redisOperator: RedisOperator,
    private val userUpdater: UserUpdater,
    private val pendingMessageHandler: RedisPendingMessageHandler
) : AbstractRedisStreamConsumer(
    redisOperator,
    "rating-update",
    "rating-consumer-group",
    "rating-consumer"
) {
    @PostConstruct
    fun init() {
        pendingMessageHandler.registerConsumer(
            streamKey,
            consumerGroupName,
            consumerName,
            this
        )
    }

    override fun processMessage(message: MapRecord<String?, Any?, Any?>?) {
        val userId = message?.value?.get("userId").toString().toLong()
        val rating = message?.value?.get("rating").toString().toInt()

        userUpdater.updateRating(userId, rating)
    }
}
package com.grepp.quizy.game.infra.redis

import com.grepp.quizy.game.domain.user.UserUpdater
import com.grepp.quizy.game.infra.redis.util.RedisOperator
import org.springframework.data.redis.connection.stream.MapRecord
import org.springframework.stereotype.Component

@Component
class RatingStreamConsumer(
    redisOperator: RedisOperator,
    private val userUpdater: UserUpdater
) : AbstractRedisStreamConsumer(
    redisOperator,
    "rating-update",
    "rating-consumer-group",
    "rating-consumer"
) {

    override fun processMessage(message: MapRecord<String?, Any?, Any?>?) {
        val userId = message?.value?.get("userId").toString().toLong()
        val rating = message?.value?.get("rating").toString().toInt()

        userUpdater.updateRating(userId, rating)
    }
}
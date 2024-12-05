package com.grepp.quizy.game.infra.redis

import com.grepp.quizy.game.domain.game.GameManager
import com.grepp.quizy.game.infra.redis.util.RedisOperator
import org.springframework.data.redis.connection.stream.MapRecord
import org.springframework.stereotype.Component

@Component
class DestroyStreamConsumer(
    redisOperator: RedisOperator,
    private val gameManager: GameManager
) : AbstractRedisStreamConsumer(
    redisOperator,
    "game-destroy",
    "game-destroy-consumer-group",
    "game-destroy-consumer"
) {
    override fun processMessage(message: MapRecord<String?, Any?, Any?>?) {
        val gameId = message?.value?.get("gameId").toString().toLong()
        gameManager.destroy(gameId)
    }
}
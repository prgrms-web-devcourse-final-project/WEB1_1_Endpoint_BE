package com.grepp.quizy.game.infra.redis

import com.grepp.quizy.game.domain.game.GamePlayService
import com.grepp.quizy.game.infra.redis.util.RedisOperator
import org.springframework.data.redis.connection.stream.MapRecord
import org.springframework.stereotype.Component

@Component
class GameStartStreamConsumer(
    redisOperator: RedisOperator,
    private val gamePlayService: GamePlayService
) : AbstractRedisStreamConsumer(
    redisOperator,
    "game-start",
    "game-start-consumer-group",
    "game-start-consumer"
) {
    override fun processMessage(message: MapRecord<String?, Any?, Any?>?) {
        val gameId = message?.value?.get("gameId").toString().toLong()
        gamePlayService.startGame(gameId)
    }
}
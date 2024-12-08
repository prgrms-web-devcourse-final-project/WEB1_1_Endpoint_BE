package com.grepp.quizy.game.infra.redis

import com.grepp.quizy.game.domain.game.GamePlayService
import com.grepp.quizy.game.infra.redis.config.RedisConsumerProperties
import com.grepp.quizy.game.infra.redis.util.RedisOperator
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.connection.stream.MapRecord
import org.springframework.stereotype.Component

@Component
class GameStartStreamConsumer(
    redisOperator: RedisOperator,
    gameStartConsumerConfig: RedisConsumerProperties,
    private val gamePlayService: GamePlayService
) : AbstractRedisStreamConsumer(
    redisOperator,
    gameStartConsumerConfig.streamKey,
    gameStartConsumerConfig.group,
    gameStartConsumerConfig.name
) {
    override fun processMessage(message: MapRecord<String?, Any?, Any?>?) {
        val gameId = message?.value?.get("gameId").toString().toLong()
        gamePlayService.startGame(gameId)
    }
}
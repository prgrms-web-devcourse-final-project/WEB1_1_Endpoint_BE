package com.grepp.quizy.game.infra.redis

import com.grepp.quizy.game.domain.game.GameManager
import com.grepp.quizy.game.infra.redis.config.RedisConsumerProperties
import com.grepp.quizy.game.infra.redis.util.RedisOperator
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.connection.stream.MapRecord
import org.springframework.stereotype.Component

@Component
class DestroyStreamConsumer(
    redisOperator: RedisOperator,
    destroyConsumerConfig: RedisConsumerProperties,
    private val gameManager: GameManager,
    private val pendingMessageHandler: RedisPendingMessageHandler
) : AbstractRedisStreamConsumer(
    redisOperator,
    destroyConsumerConfig.streamKey,
    destroyConsumerConfig.group,
    destroyConsumerConfig.name
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
        val gameId = message?.value?.get("gameId").toString().toLong()
        gameManager.destroy(gameId)
    }
}
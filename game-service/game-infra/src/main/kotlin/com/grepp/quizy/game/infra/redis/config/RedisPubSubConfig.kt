package com.grepp.quizy.game.infra.redis.config

import com.grepp.quizy.game.infra.redis.RedisSubscriber
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter

@Configuration
class RedisPubSubConfig {

    @Bean
    fun gameTopic(): ChannelTopic {
        return ChannelTopic("game")
    }

    @Bean
    fun gameMessageListenerAdapter(redisSubscriber: RedisSubscriber): MessageListenerAdapter {
        return MessageListenerAdapter(redisSubscriber, "onMessage")
    }

    @Bean
    fun redisMessageListenerContainer(
        redisConnectionFactory: RedisConnectionFactory,
        gameMessageListenerAdapter: MessageListenerAdapter,
        gameTopic: ChannelTopic,
    ): RedisMessageListenerContainer {
        val container = RedisMessageListenerContainer()
        container.connectionFactory = redisConnectionFactory
        container.addMessageListener(gameMessageListenerAdapter, gameTopic)

        return container
    }

}
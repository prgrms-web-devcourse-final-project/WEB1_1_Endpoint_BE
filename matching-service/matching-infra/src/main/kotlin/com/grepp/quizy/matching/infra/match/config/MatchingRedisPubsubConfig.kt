package com.grepp.quizy.matching.infra.match.config

import com.grepp.quizy.matching.match.PersonalMatchingSucceedEvent
import com.grepp.quizy.matching.infra.match.pubsub.MatchingRedisSubscriber
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.PatternTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class MatchingRedisPubsubConfig(
    private val redisConnectionFactory: RedisConnectionFactory
) {

    @Bean
    fun redisMessageListenerContainer(
        matchingListener: MessageListenerAdapter
    ): RedisMessageListenerContainer {
        val container = RedisMessageListenerContainer()
        container.connectionFactory = redisConnectionFactory
        container.addMessageListener(matchingListener, PatternTopic("matching"))
        return container
    }

    @Bean
    fun matchingListener(matchingRedisSubscriber: MatchingRedisSubscriber) =
        MessageListenerAdapter(matchingRedisSubscriber)

    @Bean
    fun matchingMessageTemplate(): RedisTemplate<String, PersonalMatchingSucceedEvent> {
        val template = RedisTemplate<String, PersonalMatchingSucceedEvent>()
        template.connectionFactory = redisConnectionFactory
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = Jackson2JsonRedisSerializer(PersonalMatchingSucceedEvent::class.java)
        return template
    }
}
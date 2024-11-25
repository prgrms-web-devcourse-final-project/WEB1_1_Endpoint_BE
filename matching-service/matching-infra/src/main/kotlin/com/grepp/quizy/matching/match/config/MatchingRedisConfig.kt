package com.grepp.quizy.matching.match.config

import com.grepp.quizy.matching.match.UserStatus
import com.grepp.quizy.matching.user.UserId
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class MatchingRedisConfig(
    private val redisConnectionFactory: RedisConnectionFactory
) {

    @Bean
    fun userStatusTemplate(): RedisTemplate<String, UserStatus> {
        val redisTemplate = RedisTemplate<String, UserStatus>()
        redisTemplate.connectionFactory = redisConnectionFactory
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = Jackson2JsonRedisSerializer(UserStatus::class.java)
        return redisTemplate
    }

    @Bean
    fun userIdTemplate(): RedisTemplate<String, String> {
        val redisTemplate = RedisTemplate<String, String>()
        redisTemplate.connectionFactory = redisConnectionFactory
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = StringRedisSerializer()
        return redisTemplate
    }
}
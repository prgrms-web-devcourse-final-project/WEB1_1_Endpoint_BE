package com.grepp.quizy.user.infra.redis.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {
    @Value("\${spring.data.redis.host}")
    private lateinit var host: String

    @Value("\${spring.data.redis.port}")
    private var port: Int = 0

    @Bean
    fun redisConnectionFactory(): LettuceConnectionFactory {
        return LettuceConnectionFactory(host, port)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, String> {
        val template = RedisTemplate<String, String>()
        template.connectionFactory = redisConnectionFactory()

        val stringRedisSerializer = StringRedisSerializer()

        // String 타입 key-value 직렬화 설정
        template.keySerializer = stringRedisSerializer
        template.valueSerializer = stringRedisSerializer

        // Hash Operation 직렬화 설정
        template.hashKeySerializer = stringRedisSerializer
        template.hashValueSerializer = stringRedisSerializer

        template.setEnableTransactionSupport(true)

        return template
    }
}
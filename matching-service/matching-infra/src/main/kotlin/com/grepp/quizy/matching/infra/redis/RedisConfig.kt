package com.grepp.quizy.matching.infra.redis

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
@EnableRedisRepositories(basePackages = ["com.grepp.quizy.matching"])
class RedisConfig @Autowired constructor(private val env: Environment) {

    private val host: String
        get() = env.getProperty("redis.host") ?: "localhost"

    private val port: Int
        get() = env.getProperty("redis.port")?.toInt() ?: 6379

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory =
        LettuceConnectionFactory(host, port)

    @Bean
    fun redisTemplate(connectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = GenericJackson2JsonRedisSerializer()
        template.connectionFactory = connectionFactory
        return template
    }
}
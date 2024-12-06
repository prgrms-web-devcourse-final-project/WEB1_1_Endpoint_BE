package com.grepp.quizy.matching.infra.redis.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import redis.clients.jedis.JedisPooled

@Configuration
class JedisConfig @Autowired constructor(private val env: Environment) {

    private val host: String
        get() = env.getProperty("redis.host") ?: "localhost"

    private val port: Int
        get() = env.getProperty("redis.port")?.toInt() ?: 6379

    @Bean
    fun jedis() = JedisPooled(host, port)
}
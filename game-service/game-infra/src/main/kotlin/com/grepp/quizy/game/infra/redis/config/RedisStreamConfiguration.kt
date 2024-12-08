package com.grepp.quizy.game.infra.redis.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@ConfigurationProperties(prefix = "redis.stream")
data class RedisStreamProperties(
    val consumers: Map<String, RedisConsumerProperties> = mapOf()
)

data class RedisConsumerProperties(
    val streamKey: String,
    val group: String,
    val name: String
)

@Configuration
@EnableConfigurationProperties(RedisStreamProperties::class)
class RedisStreamConfiguration {

    @Bean
    fun gameStartConsumerConfig(
        properties: RedisStreamProperties
    ): RedisConsumerProperties {
        return properties.consumers["gameStart"]
            ?: throw IllegalStateException("Game start consumer configuration not found")
    }

    @Bean
    fun ratingConsumerConfig(
        properties: RedisStreamProperties
    ): RedisConsumerProperties {
        return properties.consumers["rating"]
            ?: throw IllegalStateException("Rating consumer configuration not found")
    }

    @Bean
    fun destroyConsumerConfig(
        properties: RedisStreamProperties
    ): RedisConsumerProperties {
        return properties.consumers["destroy"]
            ?: throw IllegalStateException("Destroy consumer configuration not found")
    }

}
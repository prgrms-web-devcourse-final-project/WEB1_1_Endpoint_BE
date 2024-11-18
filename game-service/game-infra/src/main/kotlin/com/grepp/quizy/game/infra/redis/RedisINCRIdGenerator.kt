package com.grepp.quizy.game.infra.redis

import com.grepp.quizy.game.domain.IdGenerator
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class RedisINCRIdGenerator(
    private val redisTemplate: RedisTemplate<String, String>
) : IdGenerator {

    override fun generate(key: String): Long {
        return redisTemplate
            .opsForValue()
            .increment("{$key}:sequence")
            ?: throw IllegalStateException("ID 생성 실패")
    }

}
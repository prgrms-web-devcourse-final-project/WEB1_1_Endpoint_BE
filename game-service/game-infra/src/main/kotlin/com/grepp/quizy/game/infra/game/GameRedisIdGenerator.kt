package com.grepp.quizy.game.infra.game

import com.grepp.quizy.game.domain.IdGenerator
import com.grepp.quizy.game.infra.redis.RedisINCRIdGenerator
import org.springframework.stereotype.Component

@Component
class GameRedisIdGenerator (
    private val redisINCRIdGenerator: RedisINCRIdGenerator
) : IdGenerator {

    override fun generate(key: String): Long {
        return redisINCRIdGenerator.generate(key)
    }

}
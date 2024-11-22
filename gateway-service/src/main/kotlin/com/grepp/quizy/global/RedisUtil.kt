package com.grepp.quizy.global

import java.util.concurrent.TimeUnit
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class RedisUtil(
        private val redisTemplate: RedisTemplate<String, String>
) {
    private val operationValue = redisTemplate.opsForValue()
    private val operationSet = redisTemplate.opsForSet()

    fun saveValue(key: String, value: String, expirationTime: Long) {
        operationValue.set(
                key,
                value,
                expirationTime,
                TimeUnit.MICROSECONDS,
        )
    }

    fun getValue(key: String): String? {
        return operationValue[key]
    }

    fun deleteValue(key: String) {
        redisTemplate.delete(key)
    }

    fun saveSet(key: String, value: String) {
        operationSet.add(key, value)
    }

    fun isExistSet(key: String, value: String): Boolean {
        return operationSet.isMember(key, value)!!
    }
}

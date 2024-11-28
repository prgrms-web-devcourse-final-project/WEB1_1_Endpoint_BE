package com.grepp.quizy.user.infra.redis.util

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class RedisUtil(
    private val redisTemplate: RedisTemplate<String, String>
) {
    private val operationValue = redisTemplate.opsForValue()
    private val operationSet = redisTemplate.opsForSet()

    fun saveValue(key: String, value: String, expirationTime: Long) {
        operationValue.set(key, value, expirationTime, TimeUnit.MILLISECONDS)
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

    fun saveSet(key: String, value: String, expirationTime: Long) {
        operationSet.add(key, value)
        redisTemplate.expire(key, expirationTime, TimeUnit.MILLISECONDS)
    }

    fun isExistSet(key: String, value: String): Boolean {
        return operationSet.isMember(key, value)!!
    }

    fun deleteSet(key: String, value: String) {
        operationSet.remove(key, value)
    }
}
package com.grepp.quizy.matching.match.repository

import com.grepp.quizy.matching.match.MatchingPoolRepository
import com.grepp.quizy.matching.match.UserStatus
import com.grepp.quizy.matching.match.converter.toByteArray
import com.grepp.quizy.matching.user.UserId
import com.grepp.quizy.matching.user.UserVector
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class MatchingPoolRedisRepository(
    private val redisTemplate: RedisTemplate<String, Any>,
) : MatchingPoolRepository {

    override fun saveVector(userId: UserId, userVector: UserVector) {
        val key = "vector:${userId.value}"
        redisTemplate.opsForHash<String, Any>().putAll(
            key, mapOf(
                "id" to userId.value,
                "vector" to userVector.value.toByteArray()
            )
        )
    }

    override fun findNearestUser(userStatus: UserStatus): List<UserStatus> {
        TODO("Not yet implemented")
    }

    override fun remove(userId: UserId) {
        TODO("Not yet implemented")
    }
}
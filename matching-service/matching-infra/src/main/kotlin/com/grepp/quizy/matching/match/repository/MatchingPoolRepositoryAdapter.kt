package com.grepp.quizy.matching.match.repository

import com.grepp.quizy.matching.match.MATCHING_K
import com.grepp.quizy.matching.match.MATCHING_THRESHOLD
import com.grepp.quizy.matching.match.MatchingPoolRepository
import com.grepp.quizy.matching.match.UserStatus
import com.grepp.quizy.matching.match.converter.toByteArray
import com.grepp.quizy.matching.match.converter.toFloatArray
import com.grepp.quizy.matching.user.UserId
import com.grepp.quizy.matching.user.UserVector
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import kotlin.math.sqrt

private const val MATCHING_POOL_KEY = "MATCHING_POOL"

@Repository
class MatchingPoolRepositoryAdapter(
    private val redisTemplate: RedisTemplate<String, Any>,
) : MatchingPoolRepository {

    override fun saveVector(userId: UserId, userVector: UserVector) {
        val key = createMatchingPoolKey(userId)
        redisTemplate.opsForHash<String, Any>().putAll(
            key, mapOf(
                "id" to userId.value,
                "vector" to userVector.value.toByteArray()
            )
        )
    }

    override fun findNearestUser(userStatus: UserStatus): List<UserStatus> {
        val keyPattern = "$MATCHING_POOL_KEY:*"
        val keys = redisTemplate.keys(keyPattern) ?: return emptyList()

        val results = keys.mapNotNull { key ->
            val vectorBytes = redisTemplate.opsForHash<String, Any>().get(key, "vector") as ByteArray?
            val id = redisTemplate.opsForHash<String, Any>().get(key, "id") as Long?
            if (vectorBytes != null && id != null) {
                val vector = vectorBytes.toFloatArray()
                val score = cosineSimilarity(userStatus.vector.value, vector)
                Triple(id, vector, score) // score도 포함
            } else null
        }

        return results
            .filter { it.third >= MATCHING_THRESHOLD }
            .sortedByDescending { it.third } // score를 기준으로 정렬
            .take(MATCHING_K) // 상위 K개 선택
            .map { (id, vector, _) ->
                UserStatus(
                    userId = UserId(id),
                    vector = UserVector(vector)
                )
            }
    }

    override fun remove(userId: UserId) {
        redisTemplate.delete(createMatchingPoolKey(userId))
    }

    private fun createMatchingPoolKey(userId: UserId) = "$MATCHING_POOL_KEY:${userId.value}"

    private fun cosineSimilarity(vec1: FloatArray, vec2: FloatArray): Double {
        val dotProduct = vec1.indices.fold(0.0) { acc, i -> acc + vec1[i] * vec2[i] }
        val magnitude1 = sqrt(vec1.fold(0.0) { acc, v -> acc + v * v })
        val magnitude2 = sqrt(vec2.fold(0.0) { acc, v -> acc + v * v })
        return dotProduct / (magnitude1 * magnitude2)
    }
}
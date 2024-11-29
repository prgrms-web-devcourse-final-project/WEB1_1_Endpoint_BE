package com.grepp.quizy.matching.infra.match.repository

import com.grepp.quizy.matching.domain.match.MatchingQueueRepository
import com.grepp.quizy.matching.domain.match.UserStatus
import com.grepp.quizy.matching.infra.match.dto.RedisUserStatus
import com.grepp.quizy.matching.domain.user.UserId
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class MatchingQueueRepositoryAdapter(
    private val userStatusTemplate: RedisTemplate<String, RedisUserStatus>,
    private val userIdTemplate: RedisTemplate<String, String>,
) : MatchingQueueRepository {
    private val matchingQueueKey = "PENDING_QUEUE"
    private val matchingSetKey = "PENDING_SET"

    override fun enqueue(status: UserStatus) {
        userStatusTemplate.opsForList().leftPush(matchingQueueKey, RedisUserStatus.from(status))
    }

    override fun dequeue(): UserStatus? =
        userStatusTemplate.opsForList().rightPop(matchingQueueKey)?.UserStatus()

    override fun saveSet(userId: UserId) {
        userIdTemplate.opsForSet().add(matchingSetKey, userId.value.toString())
    }

    override fun removeSet(userId: UserId) {
        userIdTemplate.opsForSet().remove(matchingSetKey, userId.value.toString())
    }

    override fun isValid(userId: UserId): Boolean =
        userIdTemplate.opsForSet().isMember(matchingSetKey, userId.value.toString()) ?: false
}
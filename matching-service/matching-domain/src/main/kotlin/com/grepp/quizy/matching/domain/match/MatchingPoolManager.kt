package com.grepp.quizy.matching.domain.match

import com.grepp.quizy.matching.domain.user.UserId
import com.grepp.quizy.matching.domain.user.UserVectorizer
import org.springframework.stereotype.Component

@Component
class MatchingPoolManager(
    private val userVectorizer: UserVectorizer,
    private val matchingValidator: MatchingValidator,
    private val poolRepository: MatchingPoolRepository,
    private val queueRepository: MatchingQueueRepository
) {

    fun save(userId: UserId) {
        val status = UserStatus(userId, userVectorizer.vectorizeUser(userId))
        poolRepository.saveVector(status)
        queueRepository.enqueue(status)
        queueRepository.saveSet(userId)
    }

    fun findPivot(): UserStatus? {
        var pivot = queueRepository.dequeue() ?: return null
        while (matchingValidator.isPivotInvalid(pivot)) {
            pivot = queueRepository.dequeue() ?: return null
        }
        return pivot
    }

    fun remove(userId: UserId) {
        poolRepository.remove(userId)
        queueRepository.removeSet(userId)
    }
}
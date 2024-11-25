package com.grepp.quizy.matching.match

import com.grepp.quizy.matching.user.UserId
import com.grepp.quizy.matching.user.UserVectorizer
import org.springframework.stereotype.Component

@Component
class MatchingPoolManager(
    private val userVectorizer: UserVectorizer,
    private val poolRepository: MatchingPoolRepository,
    private val queueRepository: MatchingQueueRepository
) {

    fun save(userId: UserId) {
        val vector = userVectorizer.vectorizeUser(userId)
        poolRepository.saveVector(userId, vector)
        queueRepository.enqueue(UserStatus(userId, vector))
        queueRepository.saveSet(userId)
    }
}
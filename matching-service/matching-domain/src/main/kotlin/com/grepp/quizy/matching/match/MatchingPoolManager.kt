package com.grepp.quizy.matching.match

import com.grepp.quizy.matching.user.UserId
import com.grepp.quizy.matching.user.UserVectorizer
import org.springframework.stereotype.Component

@Component
class MatchingPoolManager(
    private val userVectorizer: UserVectorizer,
    private val poolRepository: MatchingPoolRepository
) {

    fun save(userId: UserId) {
        poolRepository.save(userId, userVectorizer.vectorizeUser(userId))
    }
}
package com.grepp.quizy.matching.user

import org.springframework.stereotype.Component

@Component
class UserVectorizer(private val userFetcher: UserFetcher) {

    fun vectorizeUser(userId: UserId): UserVector {
        val rating = userFetcher.requestUserRating(userId)
        val interests = userFetcher.requestUserInterests(userId)
        val vectorSize = GameRating.entries.size + InterestCategory.entries.size

        return UserVector(
            FloatArray(vectorSize).apply {
                this[rating.index] = 1.0f
                interests.forEach { this[it.index + GameRating.entries.size] = 1.0f }
            }
        )
    }
}
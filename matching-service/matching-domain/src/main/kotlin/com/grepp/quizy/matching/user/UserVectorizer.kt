package com.grepp.quizy.matching.user

import org.springframework.stereotype.Component

@Component
class UserVectorizer(private val userFetcher: UserFetcher) {

    fun vectorizeUser(userId: UserId): UserVector {
        val rating = userFetcher.requestUserRating(userId)
        val interests = userFetcher.requestUserInterests(userId)

        return UserVector(
            FloatArray(10).apply {
                this[GameRating.VECTOR_INDEX] = rating.index
                interests.forEach { this[it.index] = 1.0f }
            }
        )
    }
}
package com.grepp.quizy.matching.domain.user

import org.springframework.stereotype.Component

@Component
class UserVectorizer(private val userFetcher: com.grepp.quizy.matching.domain.user.UserFetcher) {

    fun vectorizeUser(userId: com.grepp.quizy.matching.domain.user.UserId): com.grepp.quizy.matching.domain.user.UserVector {
        val rating = userFetcher.requestUserRating(userId)
        val interests = userFetcher.requestUserInterests(userId)
        val vectorSize = com.grepp.quizy.matching.domain.user.GameRating.entries.size + com.grepp.quizy.matching.domain.user.InterestCategory.entries.size

        return com.grepp.quizy.matching.domain.user.UserVector(
            FloatArray(vectorSize).apply {
                this[rating.index] = 1.0f
                interests.forEach {
                    this[it.index + com.grepp.quizy.matching.domain.user.InterestCategory.Companion.VECTOR_START_INDEX] =
                        1.0f
                }
            }
        )
    }
}
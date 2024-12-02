package com.grepp.quizy.matching.domain.user

import com.grepp.quizy.matching.domain.game.GameFetcher
import com.grepp.quizy.matching.domain.game.GameRating
import org.springframework.stereotype.Component

@Component
class UserVectorizer(
    private val gameFetcher: GameFetcher,
    private val userFetcher: UserFetcher
) {

    fun vectorizeUser(userId: UserId): UserVector {
        val rating = gameFetcher.requestGameRating(userId)
        val interests = userFetcher.requestUserInterests(userId)
        val vectorSize = GameRating.entries.size + InterestCategory.entries.size

        return UserVector(
            FloatArray(vectorSize).apply {
                this[rating.index] = 1.0f
                interests.forEach {
                    this[it.index + InterestCategory.VECTOR_START_INDEX] =
                        1.0f
                }
            }
        )
    }
}
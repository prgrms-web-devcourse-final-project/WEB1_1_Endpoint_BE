package com.grepp.quizy.matching.user

import org.springframework.stereotype.Component

interface UserFetcher {
    fun requestUserRating(userId: UserId): GameRating

    fun requestUserInterests(userId: UserId): List<InterestCategory>
}

// @TODO feignclient로 요청 필요
@Component
class UserFetcherTempImpl : UserFetcher {
    override fun requestUserRating(userId: UserId): GameRating {
        return when (userId) {
            UserId(1) -> GameRating.BRONZE
            UserId(2) -> GameRating.SILVER
            UserId(3) -> GameRating.GOLD
            else -> GameRating.MASTER
        }
    }

    // 테스트용
    override fun requestUserInterests(userId: UserId): List<InterestCategory> {
        return when (userId) {
            UserId(1) -> listOf(InterestCategory.ALGORITHM, InterestCategory.DATABASE)
            UserId(2) -> listOf(InterestCategory.ALGORITHM, InterestCategory.WEB_DEVELOPMENT)
            UserId(3) -> listOf(InterestCategory.ALGORITHM, InterestCategory.DATABASE, InterestCategory.DEV_OPS)
            else -> listOf(InterestCategory.MOBILE, InterestCategory.NETWORK)
        }
    }
}
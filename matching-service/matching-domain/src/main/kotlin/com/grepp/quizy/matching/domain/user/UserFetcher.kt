package com.grepp.quizy.matching.domain.user

import org.springframework.stereotype.Component

interface UserFetcher {
    fun requestUserRating(userId: UserId): GameRating

    fun requestUserInterests(userId: UserId): List<InterestCategory>
}

// @TODO feignclient로 요청 필요
@Component
class UserFetcherTempImpl : UserFetcher {
    override fun requestUserRating(userId: UserId): GameRating {
        return GameRating.GOLD
    }

    // 테스트용
    override fun requestUserInterests(userId: UserId): List<InterestCategory> {
        return listOf(InterestCategory.ALGORITHM, InterestCategory.DATABASE, InterestCategory.DEV_OPS)
    }
}
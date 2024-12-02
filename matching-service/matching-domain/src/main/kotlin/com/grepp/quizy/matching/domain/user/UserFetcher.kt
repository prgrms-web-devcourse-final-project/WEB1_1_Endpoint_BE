package com.grepp.quizy.matching.domain.user

import org.springframework.stereotype.Component

interface UserFetcher {
    fun requestUserInterests(userId: UserId): List<InterestCategory>
}

// @TODO feignclient로 요청 필요
@Component
class UserFetcherTempImpl : UserFetcher {

    // 테스트용
    override fun requestUserInterests(userId: UserId): List<InterestCategory> {
        return listOf(InterestCategory.ALGORITHM, InterestCategory.DATABASE, InterestCategory.DEV_OPS)
    }
}
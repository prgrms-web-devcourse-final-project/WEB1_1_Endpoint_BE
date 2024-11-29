package com.grepp.quizy.matching.domain.match

import com.grepp.quizy.matching.domain.user.UserId

interface MatchingUseCase {
    fun registerWaiting(userId: UserId)
}
package com.grepp.quizy.matching.match

import com.grepp.quizy.matching.user.UserId

interface MatchingUseCase {
    fun registerWaiting(userId: UserId)
}
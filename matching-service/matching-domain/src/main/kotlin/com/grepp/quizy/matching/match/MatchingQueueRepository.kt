package com.grepp.quizy.matching.match

import com.grepp.quizy.matching.user.UserId

interface MatchingQueueRepository {
    fun enqueue(status: UserStatus)

    fun saveSet(userId: UserId)
}
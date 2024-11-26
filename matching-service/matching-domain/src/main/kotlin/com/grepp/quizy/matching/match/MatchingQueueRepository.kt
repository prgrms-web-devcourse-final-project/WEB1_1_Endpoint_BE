package com.grepp.quizy.matching.match

import com.grepp.quizy.matching.user.UserId

interface MatchingQueueRepository {
    fun enqueue(status: UserStatus)

    fun dequeue(): UserStatus?

    fun saveSet(userId: UserId)

    fun removeSet(userId: UserId)

    fun isValid(userId: UserId): Boolean
}
package com.grepp.quizy.matching.domain.match

import com.grepp.quizy.matching.domain.user.UserId
import com.grepp.quizy.matching.domain.user.UserVector

interface MatchingPoolRepository {
    fun saveVector(userStatus: UserStatus)

    fun findNearestUser(userStatus: UserStatus): List<UserStatus>

    fun remove(userId: UserId)
}
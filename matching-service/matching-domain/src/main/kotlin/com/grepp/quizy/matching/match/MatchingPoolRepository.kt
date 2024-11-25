package com.grepp.quizy.matching.match

import com.grepp.quizy.matching.user.UserId
import com.grepp.quizy.matching.user.UserVector

interface MatchingPoolRepository {
    fun save(userId: UserId, userVector: UserVector)
}
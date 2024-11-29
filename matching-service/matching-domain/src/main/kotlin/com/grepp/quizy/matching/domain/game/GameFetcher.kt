package com.grepp.quizy.matching.domain.game

import com.grepp.quizy.matching.domain.user.InterestCategory
import com.grepp.quizy.matching.domain.user.UserId

interface GameFetcher {
    fun requestGameRoomId(userIds: List<UserId>, subject: InterestCategory): GameRoomId
}
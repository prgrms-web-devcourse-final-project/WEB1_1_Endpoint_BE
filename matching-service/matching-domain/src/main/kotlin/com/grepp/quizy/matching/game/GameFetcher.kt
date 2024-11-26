package com.grepp.quizy.matching.game

import com.grepp.quizy.matching.user.InterestCategory
import com.grepp.quizy.matching.user.UserId
import org.springframework.stereotype.Component

interface GameFetcher {
    fun requestGameRoomId(userIds: List<UserId>, category: InterestCategory): GameRoomId
}

// @TODO feignclient로 요청 필요
@Component
class GameFetcherImpl : GameFetcher {
    override fun requestGameRoomId(userIds: List<UserId>, category: InterestCategory): GameRoomId {
        return GameRoomId(1L)
    }
}
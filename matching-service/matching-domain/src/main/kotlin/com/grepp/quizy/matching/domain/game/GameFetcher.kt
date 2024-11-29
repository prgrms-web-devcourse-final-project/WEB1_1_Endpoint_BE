package com.grepp.quizy.matching.domain.game

import com.grepp.quizy.matching.domain.user.InterestCategory
import com.grepp.quizy.matching.domain.user.UserId
import org.springframework.stereotype.Component

interface GameFetcher {
    fun requestGameRoomId(userIds: List<UserId>, subject: InterestCategory): GameRoomId
}

// @TODO feignclient로 요청 필요
@Component
class GameFetcherImpl : GameFetcher {
    override fun requestGameRoomId(userIds: List<UserId>, subject: InterestCategory): GameRoomId {
        return GameRoomId(1L)
    }
}
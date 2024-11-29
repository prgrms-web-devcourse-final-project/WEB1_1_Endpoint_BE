package com.grepp.quizy.matching.infra.game.adapter

import com.grepp.quizy.matching.domain.game.GameFetcher
import com.grepp.quizy.matching.domain.game.GameRoomId
import com.grepp.quizy.matching.domain.user.InterestCategory
import com.grepp.quizy.matching.domain.user.UserId
import com.grepp.quizy.matching.infra.game.client.GameRoomClient
import com.grepp.quizy.matching.infra.game.dto.GameRoomIdRequest
import org.springframework.stereotype.Component

@Component
class GameFetcherAdapter(private val gameRoomClient: GameRoomClient) : GameFetcher {
    override fun requestGameRoomId(userIds: List<UserId>, subject: InterestCategory): GameRoomId {
        val response = gameRoomClient.getGameRoomId(
            GameRoomIdRequest(userIds.map { it.value }, subject.name)
        )
        return GameRoomId(response.id)
    }
}
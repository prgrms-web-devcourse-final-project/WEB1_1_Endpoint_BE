package com.grepp.quizy.matching.match

import com.grepp.quizy.matching.game.GameRoomId
import com.grepp.quizy.matching.user.UserId

data class UserWaitingRegisteredEvent(val userId: UserId)

data class MatchingSucceedEvent(val userIds: List<UserId>, val gameRoomId: GameRoomId)

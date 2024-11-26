package com.grepp.quizy.matching.match

import com.grepp.quizy.matching.game.GameRoomId
import com.grepp.quizy.matching.user.UserId

data class UserWaitingRegisteredEvent(val userId: UserId)

data class PersonalMatchingSucceedEvent(val userId: Long, val gameRoomId: Long, val totalIds: List<Long>)

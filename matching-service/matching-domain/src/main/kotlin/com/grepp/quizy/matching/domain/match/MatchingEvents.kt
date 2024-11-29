package com.grepp.quizy.matching.domain.match

import com.grepp.quizy.matching.domain.user.UserId

data class UserWaitingRegisteredEvent(val userId: UserId)

data class PersonalMatchingSucceedEvent(val userId: Long, val gameRoomId: Long, val totalIds: List<Long>)

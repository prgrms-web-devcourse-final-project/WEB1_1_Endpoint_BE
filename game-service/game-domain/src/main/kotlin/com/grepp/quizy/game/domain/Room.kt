package com.grepp.quizy.game.domain

import com.grepp.quizy.game.domain.GameStatus.WAITING

class Room(
    val id: Long = 0,
    val status: GameStatus = WAITING,
    val playerIds: MutableSet<Long> = mutableSetOf()
) {

    fun join(userId: Long) {
        check(playerIds.contains(userId).not()) { "이미 참가한 플레이어입니다." }
        check(status == WAITING) { "게임이 이미 시작되었습니다." }
        check(playerIds.size < 5) { "인원이 초과되었습니다." }
        playerIds.add(userId)
    }

}
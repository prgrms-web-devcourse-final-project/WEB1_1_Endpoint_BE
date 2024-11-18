package com.grepp.quizy.game.domain

import com.grepp.quizy.game.domain.GameStatus.WAITING

class Room(
    val id: Long = 0,
    val status: GameStatus = WAITING,
    val playerIds: Set<Long> = setOf()
) {
}
package com.grepp.quizy.domain.game

import com.grepp.quizy.domain.game.GameStatus.WAITING

class Room(
    private val id: String,
    private val status: GameStatus = WAITING,
    private val playerIds: MutableSet<Long> = mutableSetOf()
) {
    val players: Set<Long>
        get() = playerIds
}
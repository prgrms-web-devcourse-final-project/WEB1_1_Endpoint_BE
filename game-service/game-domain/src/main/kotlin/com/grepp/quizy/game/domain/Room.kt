package com.grepp.quizy.game.domain

import com.grepp.quizy.game.domain.GameStatus.WAITING

class Room(
    private val id: String,
    private val status: GameStatus = WAITING,
    private val playerIds: MutableSet<Long> = mutableSetOf()
) {
    val players: Set<Long>
        get() = playerIds
}
package com.grepp.quizy.game.domain

sealed interface Message {
    val gameId: Long
    val type: String
}

data class RoomMessage(
    override val gameId: Long,
    val game: Game
) : Message {
    override val type: String = "ROOM"
}
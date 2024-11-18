package com.grepp.quizy.game.domain

sealed interface Message {
    val roomId: Long
    val type: String
}

data class RoomMessage(
    override val roomId: Long,
    val room: Room
) : Message {
    override val type: String = "ROOM"
}
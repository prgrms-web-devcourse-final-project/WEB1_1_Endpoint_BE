package com.grepp.quizy.game.api.room.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.grepp.quizy.game.domain.GameStatus
import com.grepp.quizy.game.domain.Room

@JsonInclude(JsonInclude.Include.NON_NULL)
class RoomResponse(
    private val id: String,
    private val status: GameStatus,
    private val playerIds: Set<Long>
) {

    companion object {
        fun from(room: Room): RoomResponse {
            return RoomResponse(
                id = room.id.toString(),
                status = room.status,
                playerIds = room.playerIds
            )
        }
    }
}
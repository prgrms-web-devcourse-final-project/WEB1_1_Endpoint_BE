package com.grepp.quizy.game.domain

import com.grepp.quizy.game.domain.exception.GameException.GameNotFoundException
import org.springframework.stereotype.Component

@Component
class RoomJoiner(
    private val roomRepository: RoomRepository
) {

    fun join(roomId: Long, userId: Long): Room {
        val room = roomRepository.findById(roomId)
            ?: throw GameNotFoundException()
        room.join(userId)
        roomRepository.save(room)
        return room
    }

}
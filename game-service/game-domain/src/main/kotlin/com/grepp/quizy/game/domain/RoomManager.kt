package com.grepp.quizy.game.domain

import com.grepp.quizy.game.domain.exception.GameException
import org.springframework.stereotype.Component

@Component
class RoomManager (
    private val roomRepository: RoomRepository
) {
    fun quit(roomId: Long, userId: Long) {
        val room = roomRepository.findById(roomId) ?: throw GameException.GameNotFoundException()
        room.quit(userId)
        roomRepository.save(room)
    }
}
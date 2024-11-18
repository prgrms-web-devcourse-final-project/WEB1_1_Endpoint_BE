package com.grepp.quizy.game.domain

import org.springframework.stereotype.Component

@Component
class RoomManager(
    private val roomRepository: RoomRepository
) {
    fun join(room: Room, userId: Long): Room {
        room.join(userId)
        roomRepository.save(room)
        return room
    }

    fun quit(room: Room, userId: Long): Room {
        room.quit(userId)
        roomRepository.save(room)
        return room
    }
}
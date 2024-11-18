package com.grepp.quizy.game.domain

import org.springframework.stereotype.Component

@Component
class RoomJoiner(
    private val roomRepository: RoomRepository
) {

    fun join(roomId: Long, userId: Long): Room {
        val room = roomRepository.findById(roomId)
            ?: throw IllegalArgumentException("존재하지 않는 방입니다.")
        room.join(userId)
        roomRepository.save(room)
        return room
    }

}
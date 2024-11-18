package com.grepp.quizy.game.domain

import com.grepp.quizy.game.domain.exception.GameException
import org.springframework.stereotype.Component

@Component
class RoomReader(
    private val roomRepository: RoomRepository
) {

    fun read(id: Long): Room {
        return roomRepository.findById(id) ?: throw GameException.GameNotFoundException()
    }

}
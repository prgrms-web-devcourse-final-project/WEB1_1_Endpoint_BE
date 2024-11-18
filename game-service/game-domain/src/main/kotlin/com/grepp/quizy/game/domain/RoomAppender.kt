package com.grepp.quizy.game.domain

import org.springframework.stereotype.Component
import java.util.*

@Component
class RoomAppender(
    private val roomRepository: RoomRepository,
    private val idGenerator: IdGenerator
) {
    fun append(): Room {
        return roomRepository.save(Room(id = idGenerator.generate("room")))
    }
}
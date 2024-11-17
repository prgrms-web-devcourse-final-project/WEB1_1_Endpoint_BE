package com.grepp.quizy.domain.game

import org.springframework.stereotype.Component
import java.util.*

@Component
class RoomAppender(
    private val roomRepository: RoomRepository
) {
    fun append() {
        roomRepository.save(Room(UUID.randomUUID().toString()))
    }
}
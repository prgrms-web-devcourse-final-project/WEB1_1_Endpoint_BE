package com.grepp.quizy.game.domain

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
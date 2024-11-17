package com.grepp.quizy.domain.game

import org.springframework.stereotype.Service

@Service
class RoomService(
    private val roomAppender: RoomAppender
) {
    fun createRoom() {
        roomAppender.append()
    }
}
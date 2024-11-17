package com.grepp.quizy.game.domain

import org.springframework.stereotype.Service

@Service
class RoomService(
    private val roomAppender: RoomAppender
) {
    fun createRoom(): Room{
        return roomAppender.append()
    }
}
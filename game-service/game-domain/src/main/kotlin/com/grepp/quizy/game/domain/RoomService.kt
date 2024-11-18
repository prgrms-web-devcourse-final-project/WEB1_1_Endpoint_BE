package com.grepp.quizy.game.domain

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoomService(
    private val roomAppender: RoomAppender,
    private val roomReader: RoomReader,
    private val roomManager: RoomManager
) {

    fun createRoom(): Room {
        return roomAppender.append()
    }

    @Transactional
    fun join(roomId: Long, userId: Long): Room {
        val room = roomReader.read(roomId)
        return roomManager.join(room, userId)
    }

    @Transactional
    fun quit(roomId: Long, userId: Long) {
        val room = roomReader.read(roomId)
        roomManager.quit(room, userId)
    }

}
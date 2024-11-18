package com.grepp.quizy.game.domain

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoomService(
    private val roomAppender: RoomAppender,
    private val roomJoiner: RoomJoiner,
    private val roomManager: RoomManager
) {

    fun createRoom(): Room {
        return roomAppender.append()
    }

    @Transactional
    fun join(roomId: Long, userId: Long): Room {
        return roomJoiner.join(roomId, userId)
    }

    fun quit(roomId: Long, userId: Long) {
        roomManager.quit(roomId, userId)
    }

}
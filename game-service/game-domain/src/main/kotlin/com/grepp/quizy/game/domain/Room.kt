package com.grepp.quizy.game.domain

import com.grepp.quizy.game.domain.GameStatus.WAITING
import com.grepp.quizy.game.domain.exception.GameException.*

class Room(
    val id: Long = 0,
    val status: GameStatus = WAITING,
    val playerIds: MutableSet<Long> = mutableSetOf()
) {

    fun join(userId: Long) {
        checkJoinable(userId)
        playerIds.add(userId)
    }

    private fun checkJoinable(userId: Long) {
        validatePlayerNotAlreadyJoined(userId)
        validateGameIsWaiting()
        validateGameHasCapacity()
    }

    private fun validatePlayerNotAlreadyJoined(userId: Long) {
        if (playerIds.contains(userId)) {
            throw GameAlreadyParticipatedException()
        }
    }

    private fun validateGameIsWaiting() {
        if (status != WAITING) {
            throw GameAlreadyStartedException()
        }
    }

    private fun validateGameHasCapacity() {
        if (playerIds.size >= 5) {
            throw GameAlreadyFullException()
        }
    }

}
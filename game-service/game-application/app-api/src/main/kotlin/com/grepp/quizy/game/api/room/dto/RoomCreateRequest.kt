package com.grepp.quizy.game.api.room.dto

import com.grepp.quizy.game.domain.GameLevel
import com.grepp.quizy.game.domain.GameSubject

class RoomCreateRequest(
    val subject: GameSubject,
    val level: GameLevel,
    val quizCount: Int,
) {
}
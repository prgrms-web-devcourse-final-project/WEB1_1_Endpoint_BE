package com.grepp.quizy.game.api.game.dto

import com.grepp.quizy.game.domain.game.GameLevel
import com.grepp.quizy.game.domain.game.GameSubject

class GameCreateRequest(
    val subject: GameSubject,
    val level: GameLevel,
    val quizCount: Int,
) {}

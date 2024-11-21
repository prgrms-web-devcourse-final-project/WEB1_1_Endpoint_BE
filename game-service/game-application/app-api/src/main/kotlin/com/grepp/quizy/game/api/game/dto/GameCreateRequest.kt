package com.grepp.quizy.game.api.game.dto

import com.grepp.quizy.game.domain.GameLevel
import com.grepp.quizy.game.domain.GameSubject

class GameCreateRequest(
        val subject: GameSubject,
        val level: GameLevel,
        val quizCount: Int,
) {}

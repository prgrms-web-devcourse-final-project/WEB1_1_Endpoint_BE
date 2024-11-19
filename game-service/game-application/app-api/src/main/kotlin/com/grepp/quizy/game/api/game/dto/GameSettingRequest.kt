package com.grepp.quizy.game.api.game.dto

import com.grepp.quizy.game.domain.GameLevel
import com.grepp.quizy.game.domain.GameSubject

data class UpdateSubjectRequest(
    val gameId: Long,
    val subject: GameSubject
)

data class UpdateLevelRequest(
    val gameId: Long,
    val level: GameLevel
)

data class UpdateQuizCountRequest(
    val gameId: Long,
    val quizCount: Int
)
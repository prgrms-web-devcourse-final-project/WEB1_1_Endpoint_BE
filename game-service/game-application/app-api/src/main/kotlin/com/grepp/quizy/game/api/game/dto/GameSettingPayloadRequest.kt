package com.grepp.quizy.game.api.game.dto

import com.grepp.quizy.game.domain.game.GameLevel
import com.grepp.quizy.game.domain.game.GameSubject

data class UpdateSubjectPayloadRequest(val subject: GameSubject)

data class UpdateLevelPayloadRequest(val level: GameLevel)

data class UpdateQuizCountPayloadRequest(val quizCount: Int)

package com.grepp.quizy.game.api.game.dto

import com.grepp.quizy.game.domain.GameLevel
import com.grepp.quizy.game.domain.GameSubject

data class UpdateSubjectPayloadRequest(val subject: GameSubject)

data class UpdateLevelPayloadRequest(val level: GameLevel)

data class UpdateQuizCountPayloadRequest(val quizCount: Int)

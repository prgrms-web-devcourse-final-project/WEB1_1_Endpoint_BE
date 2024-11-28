package com.grepp.quizy.game.api.game.dto

data class UpdateSubjectPayloadRequest(val subject: String)

data class UpdateLevelPayloadRequest(val level: String)

data class UpdateQuizCountPayloadRequest(val quizCount: Int)

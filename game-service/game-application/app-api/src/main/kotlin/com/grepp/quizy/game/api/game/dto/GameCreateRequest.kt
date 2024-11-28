package com.grepp.quizy.game.api.game.dto

class GameCreateRequest(
    val subject: String,
    val level: String,
    val quizCount: Int,
) {}

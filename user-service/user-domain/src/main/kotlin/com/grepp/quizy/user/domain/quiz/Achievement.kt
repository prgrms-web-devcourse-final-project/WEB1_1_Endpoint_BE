package com.grepp.quizy.user.domain.quiz

import java.time.LocalDateTime

data class Achievement(
    val achievementId: String,
    val title: String,
    val description: String,
    val achievedAt: LocalDateTime
)
package com.grepp.quizy.quiz.domain.user

import java.time.LocalDateTime

data class QuizUserAchievement(
    val achievementId: String,
    val achievedAt: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        fun from(achievementId: String): QuizUserAchievement {
            return QuizUserAchievement(
                achievementId = achievementId
            )
        }
    }
}
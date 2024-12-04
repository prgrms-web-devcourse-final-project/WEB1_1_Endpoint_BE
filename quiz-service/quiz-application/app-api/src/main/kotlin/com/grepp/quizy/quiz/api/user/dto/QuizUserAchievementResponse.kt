package com.grepp.quizy.quiz.api.user.dto

import com.grepp.quizy.quiz.domain.user.Achievement
import com.grepp.quizy.quiz.domain.user.QuizUserAchievement
import java.time.LocalDateTime

data class QuizUserAchievementResponse(
    val achievementId: String,
    val title: String,
    val description: String,
    val achievedAt: LocalDateTime
) {
    companion object {
        fun from(quizUserAchievement: QuizUserAchievement): QuizUserAchievementResponse {
            return Achievement.getById(quizUserAchievement.achievementId)?.let {
                QuizUserAchievementResponse(
                    achievementId = it.id,
                    title = it.title,
                    description = it.description,
                    achievedAt = quizUserAchievement.achievedAt
                )
            } ?: throw IllegalArgumentException("Invalid achievementId")
        }
    }
}
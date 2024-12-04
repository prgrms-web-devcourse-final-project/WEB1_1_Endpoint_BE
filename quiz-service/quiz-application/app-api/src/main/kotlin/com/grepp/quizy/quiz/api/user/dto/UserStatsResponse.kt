package com.grepp.quizy.quiz.api.user.dto

import com.grepp.quizy.quiz.domain.user.QuizUser

data class UserStatsResponse(
    val userId: Long,
    val achievements: List<QuizUserAchievementResponse>,
    val totalAnswered: Int,
    val correctRate: Double
) {
    companion object {
        fun from(quizUser: QuizUser): UserStatsResponse{
            return UserStatsResponse(
                userId = quizUser.id.value,
                achievements = quizUser.achievements.map { QuizUserAchievementResponse.from(it) },
                totalAnswered = quizUser.stats.totalAnswered,
                correctRate = quizUser.stats.correctRate
            )
        }
    }
}
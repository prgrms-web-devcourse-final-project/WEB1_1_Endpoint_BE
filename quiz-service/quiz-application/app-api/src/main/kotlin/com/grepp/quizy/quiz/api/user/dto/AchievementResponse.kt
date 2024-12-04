package com.grepp.quizy.quiz.api.user.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.grepp.quizy.quiz.domain.user.Achievement
import com.grepp.quizy.quiz.domain.user.QuizUserAchievement
import java.time.LocalDateTime

@JsonInclude(Include.NON_NULL)
data class AchievementResponse(
    val id: String,
    val title: String,
    val description: String,
    val isAchieved: Boolean = false,
    val achievedAt: LocalDateTime? = null
) {
    companion object {
        fun from(userAchievements: List<QuizUserAchievement>): List<AchievementResponse> {
            val achievedMap = userAchievements.associateBy { it.achievementId }

            return Achievement.getAllAchievements().map { achievement ->
                val userAchievement = achievedMap[achievement.id]
                AchievementResponse(
                    id = achievement.id,
                    title = achievement.title,
                    description = achievement.description,
                    isAchieved = userAchievement != null,
                    achievedAt = userAchievement?.achievedAt
                )
            }
        }
    }
}
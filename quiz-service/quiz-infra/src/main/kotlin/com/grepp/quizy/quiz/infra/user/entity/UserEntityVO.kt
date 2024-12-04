package com.grepp.quizy.quiz.infra.user.entity

import com.grepp.quizy.quiz.domain.quiz.QuizCategory
import com.grepp.quizy.quiz.domain.quiz.QuizDifficulty
import com.grepp.quizy.quiz.domain.user.CategoryStats
import com.grepp.quizy.quiz.domain.user.DifficultyStats
import com.grepp.quizy.quiz.domain.user.QuizUserAchievement
import com.grepp.quizy.quiz.domain.user.UserId
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.time.LocalDate
import java.time.LocalDateTime

@Embeddable
data class QuizUserInterestVO(
    @Enumerated(EnumType.STRING)
    val interest: QuizCategory
)

@Embeddable
data class QuizUserAchievementVO(
    val achievementId: String,
    val achievedAt: LocalDateTime
) {

    companion object {
        fun from(domain: QuizUserAchievement): QuizUserAchievementVO {
            return QuizUserAchievementVO(
                achievementId = domain.achievementId,
                achievedAt = domain.achievedAt
            )
        }
    }

    fun toDomain(): QuizUserAchievement {
        return QuizUserAchievement(
            achievementId = achievementId,
            achievedAt = achievedAt
        )
    }
}
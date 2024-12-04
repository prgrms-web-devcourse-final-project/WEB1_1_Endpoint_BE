package com.grepp.quizy.quiz.domain.user

import com.grepp.quizy.quiz.domain.quiz.QuizCategory
import com.grepp.quizy.quiz.domain.quiz.QuizDifficulty
import com.grepp.quizy.quiz.domain.useranswer.events.UserAnsweredEvent
import java.time.LocalDate
import java.time.LocalDateTime

data class  UserStats(
    val userId: UserId,
    // 연속 성공 관련
    val consecutiveCorrectAnswers: Int = 0,
    val maxConsecutiveCorrectAnswers: Int = 0,

    // 전체 통계
    val totalAnswered: Int = 0,
    val totalCorrect: Int = 0,
    val totalIncorrect: Int = 0,

    // 카테고리별 통계
    val categoryStats: Map<QuizCategory, CategoryStats> = emptyMap(),

    // 시간 관련
    val firstQuizAnsweredAt: LocalDateTime? = null,
    val lastAnsweredAt: LocalDateTime? = null,

    // 참여도/활동성
    val dailyStreak: Int = 0,
    val maxDailyStreak: Int = 0,
    val lastActiveDate: LocalDate? = null,

    // 난이도별 통계
    val difficultyStats: Map<QuizDifficulty, DifficultyStats> = emptyMap()
) {
    val correctRate: Double
        get() = if (totalAnswered > 0) totalCorrect.toDouble() / totalAnswered else 0.0

    fun update(event: UserAnsweredEvent): UserStats =
        copy(
            totalAnswered = totalAnswered + 1,
            totalCorrect = totalCorrect + if (event.isCorrect) 1 else 0,
            totalIncorrect = totalIncorrect + if (!event.isCorrect) 1 else 0,
            consecutiveCorrectAnswers = if (event.isCorrect)
                consecutiveCorrectAnswers + 1 else 0,
            maxConsecutiveCorrectAnswers = maxOf(
                maxConsecutiveCorrectAnswers,
                if (event.isCorrect) consecutiveCorrectAnswers + 1 else 0
            ),
            lastAnsweredAt = event.answeredAt,
            firstQuizAnsweredAt = firstQuizAnsweredAt ?: event.answeredAt,
            categoryStats = updateCategoryStats(event),
            difficultyStats = updateDifficultyStats(event),
            dailyStreak = calculateDailyStreak(event.answeredAt.toLocalDate()),
            maxDailyStreak = calculateMaxDailyStreak(event.answeredAt.toLocalDate()),
            lastActiveDate = event.answeredAt.toLocalDate()
        )

    private fun updateCategoryStats(event: UserAnsweredEvent): Map<QuizCategory, CategoryStats> {
        val currentStats = categoryStats[event.category] ?: CategoryStats()
        return categoryStats + (event.category to currentStats.copy(
            answered = currentStats.answered + 1,
            correct = currentStats.correct + if (event.isCorrect) 1 else 0,
            incorrect = currentStats.incorrect + if (!event.isCorrect) 1 else 0
        ))
    }

    private fun updateDifficultyStats(event: UserAnsweredEvent): Map<QuizDifficulty, DifficultyStats> {
        val currentStats = difficultyStats[event.difficulty] ?: DifficultyStats()
        return difficultyStats + (event.difficulty to currentStats.copy(
            attempted = currentStats.attempted + 1,
            completed = currentStats.completed + if (event.isCorrect) 1 else 0,
            averageScore = calculateNewAverage(
                currentStats.averageScore,
                currentStats.attempted,
                if (event.isCorrect) 1.0 else 0.0
            )
        ))
    }

    private fun calculateDailyStreak(newDate: LocalDate): Int = when {
        lastActiveDate == null -> 1
        lastActiveDate == newDate -> dailyStreak
        lastActiveDate.plusDays(1) == newDate -> dailyStreak + 1
        else -> 1
    }

    private fun calculateMaxDailyStreak(newDate: LocalDate): Int = when {
        lastActiveDate == null -> 1
        lastActiveDate == newDate -> maxDailyStreak
        lastActiveDate.plusDays(1) == newDate -> maxOf(maxDailyStreak, dailyStreak + 1)
        else -> maxDailyStreak
    }

    private fun calculateNewAverage(currentAverage: Double, count: Int, newValue: Double): Double =
        (currentAverage * count + newValue) / (count + 1)
}

data class CategoryStats(
    val answered: Int = 0,
    val correct: Int = 0,
    val incorrect: Int = 0
)

data class DifficultyStats(
    val attempted: Int = 0,
    val completed: Int = 0,
    val averageScore: Double = 0.0
)
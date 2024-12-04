package com.grepp.quizy.quiz.infra.user.entity

import com.grepp.quizy.quiz.domain.quiz.QuizCategory
import com.grepp.quizy.quiz.domain.quiz.QuizDifficulty
import com.grepp.quizy.quiz.domain.user.CategoryStats
import com.grepp.quizy.quiz.domain.user.DifficultyStats
import com.grepp.quizy.quiz.domain.user.UserId
import com.grepp.quizy.quiz.domain.user.UserStats
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "user_stats")
class UserStatsEntity(
    @Id
    val userId: Long,
    val consecutiveCorrectAnswers: Int = 0,
    val maxConsecutiveCorrectAnswers: Int = 0,
    val totalAnswered: Int = 0,
    val totalCorrect: Int = 0,
    val totalIncorrect: Int = 0,
    val firstQuizAnsweredAt: LocalDateTime? = null,
    val lastAnsweredAt: LocalDateTime? = null,
    val dailyStreak: Int = 0,
    val maxDailyStreak: Int = 0,
    val lastActiveDate: LocalDate? = null,

    @OneToMany(mappedBy = "userStats", cascade = [CascadeType.ALL], orphanRemoval = true)
    private val categoryStats: MutableSet<CategoryStatsEntity> = mutableSetOf(),

    @OneToMany(mappedBy = "userStats", cascade = [CascadeType.ALL], orphanRemoval = true)
    private val difficultyStats: MutableSet<DifficultyStatsEntity> = mutableSetOf()
) {
    fun toDomain(): UserStats =
        UserStats(
            userId = UserId(userId),
            consecutiveCorrectAnswers = consecutiveCorrectAnswers,
            maxConsecutiveCorrectAnswers = maxConsecutiveCorrectAnswers,
            totalAnswered = totalAnswered,
            totalCorrect = totalCorrect,
            totalIncorrect = totalIncorrect,
            firstQuizAnsweredAt = firstQuizAnsweredAt,
            lastAnsweredAt = lastAnsweredAt,
            dailyStreak = dailyStreak,
            maxDailyStreak = maxDailyStreak,
            lastActiveDate = lastActiveDate,
            categoryStats = categoryStats.associate { it.toDomainPair() },
            difficultyStats = difficultyStats.associate { it.toDomainPair() }
        )

    companion object {
        fun from(domain: UserStats): UserStatsEntity =
            UserStatsEntity(
                userId = domain.userId.value,
                consecutiveCorrectAnswers = domain.consecutiveCorrectAnswers,
                maxConsecutiveCorrectAnswers = domain.maxConsecutiveCorrectAnswers,
                totalAnswered = domain.totalAnswered,
                totalCorrect = domain.totalCorrect,
                totalIncorrect = domain.totalIncorrect,
                firstQuizAnsweredAt = domain.firstQuizAnsweredAt,
                lastAnsweredAt = domain.lastAnsweredAt,
                dailyStreak = domain.dailyStreak,
                maxDailyStreak = domain.maxDailyStreak,
                lastActiveDate = domain.lastActiveDate,
            ).apply {
                categoryStats.addAll(domain.categoryStats.map { CategoryStatsEntity.from(userId, it.key, it.value) })
                difficultyStats.addAll(domain.difficultyStats.map { DifficultyStatsEntity.from(userId, it.key, it.value) })
            }
    }
}


@Entity
@Table(name = "category_stats", uniqueConstraints = [UniqueConstraint(columnNames = ["user_id", "category"])])
class CategoryStatsEntity(
    @EmbeddedId
    val id: CategoryStatsId,
    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val userStats: UserStatsEntity,
    val answered: Int,
    val correct: Int,
    val incorrect: Int
) {
    fun toDomainPair() = id.category to CategoryStats(answered, correct, incorrect)

    companion object {
        fun from(userId: Long, category: QuizCategory, domain: CategoryStats) =
            CategoryStatsEntity(
                id = CategoryStatsId(userId, category),
                userStats = UserStatsEntity(userId),
                answered = domain.answered,
                correct = domain.correct,
                incorrect = domain.incorrect
            )
    }
}


@Entity
@Table(name = "difficulty_stats", uniqueConstraints = [UniqueConstraint(columnNames = ["user_id", "difficulty"])])
class DifficultyStatsEntity(
    @EmbeddedId
    val id: DifficultyStatsId,

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val userStats: UserStatsEntity,
    val attempted: Int,
    val completed: Int,
    val averageScore: Double
) {
    fun toDomainPair() = id.difficulty to DifficultyStats(attempted, completed, averageScore)

    companion object {
        fun from(userId: Long, difficulty: QuizDifficulty, domain: DifficultyStats) =
            DifficultyStatsEntity(
                id = DifficultyStatsId(userId, difficulty),
                userStats = UserStatsEntity(userId),
                attempted = domain.attempted,
                completed = domain.completed,
                averageScore = domain.averageScore
            )
    }
}

@Embeddable
data class CategoryStatsId(
    @Column(name = "user_id")
    val userId: Long,

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    val category: QuizCategory
)

@Embeddable
data class DifficultyStatsId(
    @Column(name = "user_id")
    val userId: Long,

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty")
    val difficulty: QuizDifficulty
)
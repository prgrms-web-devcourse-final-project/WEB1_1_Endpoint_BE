package com.grepp.quizy.quiz.infra.user.cache

import com.grepp.quizy.quiz.domain.quiz.QuizCategory
import com.grepp.quizy.quiz.domain.user.QuizUser
import com.grepp.quizy.quiz.domain.user.QuizUserAchievement
import com.grepp.quizy.quiz.domain.user.UserId
import com.grepp.quizy.quiz.domain.user.UserStats
import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import java.time.Duration

@RedisHash("quizUser")
class QuizUserRedisEntity(
    @Id
    val id: Long,

    @TimeToLive
    private val ttl: Long = Duration.ofHours(1).toMillis(),

    val name: String,
    val imgPath: String,
    val interests: List<QuizCategory> = emptyList(),
    val achievements: List<QuizUserAchievement> = emptyList(),
    val stats: UserStats
) {
    companion object {
        fun from(quizUser: QuizUser): QuizUserRedisEntity {
            return QuizUserRedisEntity(
                id = quizUser.id.value,
                name = quizUser.name,
                imgPath = quizUser.imgPath,
                interests = quizUser.interests,
                achievements = quizUser.achievements,
                stats = quizUser.stats
            )
        }
    }

    fun toDomain(): QuizUser {
        return QuizUser(
            id = UserId(id),
            _name = name,
            _imgPath = imgPath,
            _interests = interests.toMutableList(),
            _achievements = achievements.toMutableList(),
            _stats = stats
        )
    }
}
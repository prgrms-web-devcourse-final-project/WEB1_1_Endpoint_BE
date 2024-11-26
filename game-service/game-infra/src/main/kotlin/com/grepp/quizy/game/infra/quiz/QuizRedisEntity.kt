package com.grepp.quizy.game.infra.quiz

import com.grepp.quizy.game.domain.quiz.GameQuiz
import com.grepp.quizy.game.domain.quiz.GameQuizAnswer
import com.grepp.quizy.game.domain.quiz.GameQuizOption
import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive

private const val HOURS_IN_SECOND = 3600L

@RedisHash("quiz")
class QuizRedisEntity(

    @Id
    val id: Long = 0L,

    @TimeToLive
    private val ttl: Long = HOURS_IN_SECOND,

    val content: String,
    val option: List<GameQuizOption>,
    val answer: GameQuizAnswer

) {

    companion object {

        fun from(gameQuiz: GameQuiz): QuizRedisEntity =
            QuizRedisEntity(
                id = gameQuiz.id,
                content = gameQuiz.content,
                option = gameQuiz.option,
                answer = gameQuiz.answer
            )
    }

    fun toDomain(): GameQuiz {
        return GameQuiz(
            id = id,
            content = content,
            option = option,
            answer = answer
        )
    }

}
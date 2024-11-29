package com.grepp.quizy.game.infra.game.repository

import com.grepp.quizy.game.domain.game.GameQuizRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class GameQuizRepositoryAdapter(
    private val redisTemplate: RedisTemplate<String, String>,
) : GameQuizRepository {
    override fun saveQuiz(gameId: Long, quizId: Long): Long? {
        val quizSetKey = "game:$gameId:quizzes"
        return redisTemplate.opsForSet().add(quizSetKey, quizId.toString())
    }

    override fun findQuizzes(gameId: Long): Set<Long> {
        val quizSetKey = "game:$gameId:quizzes"
        return redisTemplate.opsForSet().members(quizSetKey)
            ?.map { it.toLong() }
            ?.toSet()
            ?: emptySet()
    }
}
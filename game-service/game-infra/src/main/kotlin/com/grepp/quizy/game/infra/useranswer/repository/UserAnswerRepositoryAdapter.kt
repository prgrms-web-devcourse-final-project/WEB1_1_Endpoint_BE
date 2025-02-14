package com.grepp.quizy.game.infra.useranswer.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.grepp.quizy.game.domain.useranswer.UserAnswer
import com.grepp.quizy.game.domain.useranswer.UserAnswerRepository
import com.grepp.quizy.game.infra.useranswer.entity.UserAnswerRedis
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class UserAnswerRepositoryAdapter(
    private val redisTemplate: RedisTemplate<String, String>,
    private val objectMapper: ObjectMapper
) : UserAnswerRepository {

    override fun save(userAnswer: UserAnswer): UserAnswer {
        val userAnswerKey = "game:${userAnswer.gameId}:userAnswer:${userAnswer.userId}"
        val userAnswerRedis = UserAnswerRedis.from(userAnswer)
        redisTemplate.opsForList().rightPush(userAnswerKey, objectMapper.writeValueAsString(userAnswerRedis))
        return userAnswer
    }

    override fun findAllByGameIdAndUserId(gameId: Long, userId: Long): List<UserAnswer> {
        val userAnswerKey = "game:$gameId:userAnswer:$userId"
        return redisTemplate.opsForList().range(userAnswerKey, 0, -1)
            ?.map { objectMapper.readValue(it, UserAnswerRedis::class.java).toDomain(gameId, userId) }
            ?: emptyList()
    }

}
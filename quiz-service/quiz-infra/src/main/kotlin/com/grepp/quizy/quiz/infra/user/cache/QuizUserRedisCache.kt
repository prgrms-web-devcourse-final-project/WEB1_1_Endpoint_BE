package com.grepp.quizy.quiz.infra.user.cache

import com.grepp.quizy.quiz.domain.user.QuizUser
import com.grepp.quizy.quiz.domain.user.QuizUserCache
import com.grepp.quizy.quiz.domain.user.UserId
import org.springframework.data.redis.core.ScanOptions
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.lang.Long

@Repository
class QuizUserRedisCache(
    private val quizUserRedisRepository: QuizUserRedisRepository,
    private val redisTemplate: StringRedisTemplate
) : QuizUserCache {
    override fun cache(quizUser: QuizUser): QuizUser {
        return quizUserRedisRepository
            .save(QuizUserRedisEntity.from(quizUser))
            .toDomain()
    }

    override fun getById(userId: UserId): QuizUser? {
        return quizUserRedisRepository.findByIdOrNull(userId.value)?.toDomain()
    }

    fun findAllKeys(): List<UserId> {
        val keys = mutableListOf<UserId>()

        val scanOptions = ScanOptions.scanOptions()
            .match("quizUser:*") // 키 패턴 지정
            .count(100) // 한 번에 가져올 키 개수
            .build()

        redisTemplate.execute { connection ->
            val cursor = connection.commands().scan(scanOptions)
            cursor.use { c ->
                while (c.hasNext()) {
                    val key = String(c.next(), Charsets.UTF_8).split(":")[1].toLong()
                    keys.add(UserId(key))
                }
            }
        }
        return keys
    }
}
package com.grepp.quizy.quiz.infra.quiz.cache

import com.grepp.quizy.quiz.domain.like.Like
import com.grepp.quizy.quiz.domain.quiz.QuizCache
import com.grepp.quizy.quiz.domain.quiz.QuizId
import com.grepp.quizy.quiz.infra.quiz.repository.QuizJpaRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.Duration
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

@Component
class QuizRedisCache(
        private val redisTemplate: StringRedisTemplate,
        private val quizJpaRepository: QuizJpaRepository,
) : QuizCache {

    companion object {
        public const val QUIZ_LIKE_KEY = "quiz:like:"
        private const val EXPIRE_DAYS = 7L
    }

    override fun increaseLikeCount(quizId: QuizId): Long {
        val countKey = "$QUIZ_LIKE_KEY${quizId.value}:count"
        return redisTemplate.opsForValue().increment(countKey)?.also {
            redisTemplate.expire(
                    countKey,
                    Duration.ofDays(EXPIRE_DAYS),
            )
        } ?: throw IllegalStateException("좋아요 수 증가 실패")
    }

    override fun decreaseLikeCount(quizId: QuizId): Long {
        val countKey = "$QUIZ_LIKE_KEY${quizId.value}:count"
        return redisTemplate.opsForValue().decrement(countKey)
                ?: throw IllegalStateException("좋아요 수 감소 실패")
    }

    override fun cacheLike(like: Like) {
        val userLikeKey =
                "$QUIZ_LIKE_KEY${like.quizId.value}:${like.likerId.value}"
        redisTemplate
                .opsForValue()
                .set(userLikeKey, "1", Duration.ofDays(EXPIRE_DAYS))
    }

    override fun deleteLike(like: Like) {
        val userLikeKey =
                "$QUIZ_LIKE_KEY${like.quizId.value}:${like.likerId.value}"
        redisTemplate.delete(userLikeKey)
    }

    override fun isLikeCached(like: Like): Boolean? {
        val userLikeKey =
                "$QUIZ_LIKE_KEY${like.quizId.value}:${like.likerId.value}"
        return redisTemplate.opsForValue().get(userLikeKey)?.let {
            true
        }
    }
}

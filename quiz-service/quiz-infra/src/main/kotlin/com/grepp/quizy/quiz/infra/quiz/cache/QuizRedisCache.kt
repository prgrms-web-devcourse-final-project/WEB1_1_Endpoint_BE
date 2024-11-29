package com.grepp.quizy.quiz.infra.quiz.cache

import com.grepp.quizy.quiz.domain.like.Like
import com.grepp.quizy.quiz.domain.quiz.QuizCache
import com.grepp.quizy.quiz.domain.quiz.QuizId
import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.Duration
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

@Component
class QuizRedisCache(private val redisTemplate: StringRedisTemplate) :
        QuizCache {

    companion object {
        const val QUIZ_LIKE_KEY = "quiz:like:"
        const val QUIZ_SELECTION_KEY = "quiz:selection:"
        private const val EXPIRE_DAYS = 7L
    }

    override fun increaseLikeCount(quizId: QuizId) {
        val countKey = "$QUIZ_LIKE_KEY${quizId.value}:count"
        redisTemplate.opsForValue().increment(countKey)?.also {
            redisTemplate.expire(
                    countKey,
                    Duration.ofDays(EXPIRE_DAYS),
            )
        } ?: throw IllegalStateException("좋아요 수 증가 실패")
    }

    override fun decreaseLikeCount(quizId: QuizId) {
        val countKey = "$QUIZ_LIKE_KEY${quizId.value}:count"
        redisTemplate.opsForValue().decrement(countKey)
                ?: throw IllegalStateException("좋아요 수 감소 실패")
    }

    override fun cacheLike(like: Like) {
        val key = "$QUIZ_LIKE_KEY${like.quizId.value}"
        redisTemplate
                .opsForSet()
                .add(key, like.likerId.value.toString())
        redisTemplate.expire(key, Duration.ofDays(EXPIRE_DAYS))
    }

    override fun deleteLike(like: Like) {
        val key = "$QUIZ_LIKE_KEY${like.quizId.value}"
        redisTemplate
                .opsForSet()
                .remove(key, like.likerId.value.toString())
    }

    override fun isLikeCached(like: Like): Boolean? {
        val key = "$QUIZ_LIKE_KEY${like.quizId.value}"
        return redisTemplate
                .opsForSet()
                .isMember(key, like.likerId.value.toString())
    }

    override fun increaseSelectionCount(quizId: QuizId, optionNumber: Int) {
        val countKey = "$QUIZ_SELECTION_KEY${quizId.value}:$optionNumber:count"
        redisTemplate.opsForValue().increment(countKey)?.also {
            redisTemplate.expire(
                    countKey,
                    Duration.ofDays(EXPIRE_DAYS),
            )
        } ?: throw IllegalStateException("옵션 선택 수 증가 실패")
    }

    override fun decreaseSelectionCount(quizId: QuizId, optionNumber: Int) {
        val countKey = "$QUIZ_SELECTION_KEY${quizId.value}:$optionNumber:count"
        redisTemplate.opsForValue().decrement(countKey)
                ?: throw IllegalStateException("옵션 선택 수 감소 실패")
    }
}

package com.grepp.quizy.quiz.infra.quiz.scheduled

import com.grepp.quizy.quiz.infra.quiz.cache.QuizRedisCache.Companion.QUIZ_LIKE_KEY
import com.grepp.quizy.quiz.infra.quiz.repository.QuizJpaRepository
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock
import org.springframework.data.redis.core.ScanOptions
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class QuizLikeSynchronizer(
        private val redisTemplate: StringRedisTemplate,
        private val quizJpaRepository: QuizJpaRepository,
) {
    companion object {
        private const val SCHEDULED_FIXED_RATE = 10000L // 10초
    }

    @SchedulerLock(
            name = "like_sync_lock",
            lockAtMostFor = "PT9S",
            lockAtLeastFor = "PT3S",
    )
    @Scheduled(fixedRate = SCHEDULED_FIXED_RATE)
    fun synchronizeLikeCounts() {
        val scanOptions =
                ScanOptions.scanOptions()
                        .match("$QUIZ_LIKE_KEY*:count")
                        .count(100)
                        .build()

        val quizIdToCountMap = mutableMapOf<Long, Long>()
        val keysToDelete = mutableListOf<String>()

        redisTemplate.execute { connection ->
            val cursor = connection.commands().scan(scanOptions)
            cursor.use { c ->
                while (c.hasNext()) {
                    val key = String(c.next(), Charsets.UTF_8)
                    val quizId =
                            key.substring(
                                            QUIZ_LIKE_KEY.length,
                                            key.lastIndexOf(":count"),
                                    )
                                    .toLong()
                    val count =
                            redisTemplate
                                    .opsForValue()
                                    .get(key)
                                    ?.toLongOrNull() ?: 0L
                    quizIdToCountMap[quizId] = count
                    keysToDelete.add(key)
                }
            }
        }
        quizJpaRepository.batchUpdate(quizIdToCountMap)
        redisTemplate.delete(keysToDelete)
    }
}

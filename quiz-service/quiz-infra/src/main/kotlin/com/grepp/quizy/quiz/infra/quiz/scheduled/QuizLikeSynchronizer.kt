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

        val previousCounts = mutableMapOf<String, Long>()
        val quizIdToCountMap = mutableMapOf<Long, Long>()

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
                    previousCounts[key] = count
                }
            }
        }
        quizJpaRepository.batchUpdate(quizIdToCountMap)

        previousCounts.forEach { (key, previousCount) ->
            val currentCount =
                    redisTemplate
                            .opsForValue()
                            .get(key)
                            ?.toLongOrNull() ?: 0L

            if (currentCount > previousCount) {
                // 새로운 차이값만 남기기
                redisTemplate
                        .opsForValue()
                        .set(
                                key,
                                (currentCount - previousCount)
                                        .toString(),
                        )
            } else {
                // 변화가 없다면 키 삭제
                redisTemplate.delete(key)
            }
        }
    }
}

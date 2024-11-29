package com.grepp.quizy.quiz.infra.quiz.scheduled

import com.grepp.quizy.quiz.infra.quiz.cache.QuizRedisCache.Companion.QUIZ_SELECTION_KEY
import com.grepp.quizy.quiz.infra.quiz.repository.QuizJpaRepository
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock
import org.springframework.data.redis.core.ScanOptions
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class QuizOptionSelectionCountSynchronizer(
    private val stringRedisTemplate: StringRedisTemplate,
    private val quizJpaRepository: QuizJpaRepository
) : AbstractCountSynchronizer() {

    @SchedulerLock(
        name = "option_selection_sync_lock",
        lockAtMostFor = "PT9S",
        lockAtLeastFor = "PT3S",
    )
    @Scheduled(fixedRate = SCHEDULED_FIXED_RATE)
    override fun synchronize() {
        val scanOptions = ScanOptions.scanOptions()
            .match("$QUIZ_SELECTION_KEY*:*:count")
            .count(100)
            .build()

        val quizOptionCountMap = mutableMapOf<Long, MutableMap<Int, Long>>()

        stringRedisTemplate.execute { connection ->
            val cursor = connection.commands().scan(scanOptions)
            cursor.use { c ->
                while (c.hasNext()) {
                    val key = String(c.next(), Charsets.UTF_8)
                    // key format: QUIZ_SELECTION_KEY{quizId}:{optionNumber}:count
                    val (quizId, optionNumber) = parseKey(key)
                    val count = stringRedisTemplate.opsForValue()
                        .getAndDelete(key)
                        ?.toLongOrNull() ?: 0L

                    quizOptionCountMap
                        .getOrPut(quizId) { mutableMapOf() }[optionNumber] = count
                }
            }
        }

        quizJpaRepository.selectionCountBatchUpdate(quizOptionCountMap)
    }

    private fun parseKey(key: String): Pair<Long, Int> {
        val parts = key.removePrefix(QUIZ_SELECTION_KEY)
            .removeSuffix(":count")
            .split(":")
        return Pair(parts[0].toLong(), parts[1].toInt())
    }
}
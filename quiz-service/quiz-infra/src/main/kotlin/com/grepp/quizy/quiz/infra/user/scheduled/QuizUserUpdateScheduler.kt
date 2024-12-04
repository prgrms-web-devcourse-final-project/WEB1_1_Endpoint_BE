package com.grepp.quizy.quiz.infra.user.scheduled

import com.grepp.quizy.quiz.infra.user.cache.QuizUserRedisCache
import com.grepp.quizy.quiz.infra.user.entity.QuizUserEntity
import com.grepp.quizy.quiz.infra.user.repository.QuizUserJpaRepository
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class QuizUserUpdateScheduler(
    private val quizUserRedisCache: QuizUserRedisCache,
    private val quizUserJpaRepository: QuizUserJpaRepository
) {
    @SchedulerLock(
        name = "user_sync_lock",
        lockAtMostFor = "PT9S",
        lockAtLeastFor = "PT3S",
    )
    @Scheduled(fixedRate = 30000L)
    fun synchronize() {
        val usersToUpdate = quizUserRedisCache.findAllKeys().mapNotNull {
            quizUserRedisCache.getById(it)
        }
        quizUserJpaRepository.saveAll(usersToUpdate.map { QuizUserEntity.from(it) })
    }
}
package com.grepp.quizy.quiz.infra.quiz.scheduled

import com.grepp.quizy.quiz.infra.quiz.scheduled.CountSynchronizer.Companion.SCHEDULED_FIXED_RATE
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class CountScheduler(
    private val countSynchronizers: List<CountSynchronizer>
) {
    @SchedulerLock(
        name = "count_sync_lock",
        lockAtMostFor = "PT9S",
        lockAtLeastFor = "PT3S",
    )
    @Scheduled(fixedRate = SCHEDULED_FIXED_RATE)
    fun synchronize() {
        countSynchronizers.forEach { it.synchronize() }
    }
}
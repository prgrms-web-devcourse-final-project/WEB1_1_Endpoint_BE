package com.grepp.quizy.matching.match

import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class MatchingEventHandler(
    private val matchingPoolManager: MatchingPoolManager,
    private val matchingManager: MatchingManager
) {

    @Async
    @EventListener(UserWaitingRegisteredEvent::class)
    fun handleUserWaitingRegistered(event: UserWaitingRegisteredEvent) {
        val pivot = matchingPoolManager.findPivot() ?: return
        matchingManager.match(pivot)
    }
}
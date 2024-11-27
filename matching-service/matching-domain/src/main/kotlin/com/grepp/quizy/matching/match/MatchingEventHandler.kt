package com.grepp.quizy.matching.match

import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class MatchingEventHandler(
    private val matchingPoolManager: MatchingPoolManager,
    private val matchingManager: MatchingManager,
    private val matchingValidator: MatchingValidator
) {

    @Async
    @EventListener(UserWaitingRegisteredEvent::class)
    fun handleUserWaitingRegistered(event: UserWaitingRegisteredEvent) {
        val pivot = getPivot() ?: return
        matchingManager.match(pivot)
    }

    fun getPivot(): UserStatus? {
        var pivot = matchingPoolManager.findPivot() ?: return null
        while (matchingValidator.isPivotInvalid(pivot)) {
            pivot = matchingPoolManager.findPivot() ?: return null
        }
        return pivot
    }
}
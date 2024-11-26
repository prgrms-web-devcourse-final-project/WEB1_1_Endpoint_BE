package com.grepp.quizy.matching.match

import com.grepp.quizy.matching.user.UserId
import org.springframework.stereotype.Service

@Service
class MatchingService(
    private val matchingPoolManager: MatchingPoolManager,
    private val matchingEventPublisher: MatchingEventPublisher
) : MatchingUseCase {

    override fun registerWaiting(userId: UserId) {
        matchingPoolManager.save(userId)
        matchingEventPublisher.publish(UserWaitingRegisteredEvent(userId))
    }
}
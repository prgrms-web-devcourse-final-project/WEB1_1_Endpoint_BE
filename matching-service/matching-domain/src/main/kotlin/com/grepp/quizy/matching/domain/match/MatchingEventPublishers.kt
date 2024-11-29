package com.grepp.quizy.matching.domain.match

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class MatchingEventInternalPublisher(
    private val eventPublisher: ApplicationEventPublisher
) {
    fun publish(event: UserWaitingRegisteredEvent) {
        eventPublisher.publishEvent(event)
    }
}

interface MatchingEventPublisher {
    fun publish(event: PersonalMatchingSucceedEvent)
}

interface MatchingEventSender {
    fun send(event: PersonalMatchingSucceedEvent)
}

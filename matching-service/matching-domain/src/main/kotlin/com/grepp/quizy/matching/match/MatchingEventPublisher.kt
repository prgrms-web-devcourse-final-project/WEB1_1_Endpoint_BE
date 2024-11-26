package com.grepp.quizy.matching.match

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class MatchingEventPublisher(private val eventPublisher: ApplicationEventPublisher) {
    fun publish(event: UserWaitingRegisteredEvent) {
        eventPublisher.publishEvent(event)
    }
}
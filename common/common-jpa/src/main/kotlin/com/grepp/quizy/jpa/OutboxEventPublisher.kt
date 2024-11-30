package com.grepp.quizy.jpa

import com.grepp.quizy.common.event.EventPublisher
import com.grepp.quizy.common.event.ServiceEvent
import org.springframework.stereotype.Repository

@Repository
class OutboxEventPublisher(
    private val outboxEventJpaRepository: OutboxEventJpaRepository
) : EventPublisher {

    override fun publish(event: ServiceEvent) {
        val outboxEvent = OutboxEventEntity.from(event)
        outboxEventJpaRepository.save(outboxEvent)
    }
}
package com.grepp.quizy.quiz.infra.user.messaging.listener

import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

@Component
class UserEventKafkaListener(
    private val eventHandlerFactory: EventHandlerFactory,
) {

    @KafkaListener(topics = ["\${kafka.topic.user-outbox}"], groupId = "\${kafka.consumer-group.quiz}")
    fun receive(records: List<ConsumerRecord<String, Event>>, ack: Acknowledgment) {
        records.forEach { record ->
            val appEvent = record.value()
            log.info { "User Outbox 이벤트를 받았습니다. $appEvent" }
            eventHandlerFactory.getEventHandler(appEvent.origin).process(appEvent)
        }
        ack.acknowledge()
    }
}
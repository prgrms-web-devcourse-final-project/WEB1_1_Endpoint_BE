package com.grepp.quizy.game.infra.user.messaging.listener

import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

@Component
class UserKafkaListener(
    private val eventHandlerFactory: EventHandlerFactory
) {
    @KafkaListener(
        groupId = "\${kafka.consumer-group.game}",
        topics = ["\${kafka.topic.user-create}", "\${kafka.topic.user-update}", "\${kafka.topic.user-delete}"]
    )
    fun receive(records: List<ConsumerRecord<String, Event>>, ack: Acknowledgment) {
        records.forEach { record ->
            val appEvent = record.value()
            log.info { "User Outbox 이벤트를 받았습니다. ${appEvent}" }
            eventHandlerFactory.getEventHandler(appEvent.origin).process(appEvent)
        }
        ack.acknowledge()
    }
}
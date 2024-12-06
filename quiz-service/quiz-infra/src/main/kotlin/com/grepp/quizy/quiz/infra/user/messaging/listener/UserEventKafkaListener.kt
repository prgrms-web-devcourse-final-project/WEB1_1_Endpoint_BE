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

    @KafkaListener(
        topics = ["#{'\${kafka.topic.user-outbox}'.split(',')}"],
        groupId = "\${kafka.consumer-group.quiz}"
    )
    fun receive(record: ConsumerRecord<String, Event>, ack: Acknowledgment) {
        val appEvent = record.value()
        log.info { "User Outbox 이벤트를 받았습니다. $appEvent" }

        try {
            eventHandlerFactory.getEventHandler(appEvent.origin).process(appEvent)
            ack.acknowledge()
        } catch (ex: Exception) {
            log.error(ex) { "이벤트 처리 중 오류 발생: $appEvent" }
            // ack.acknowledge()를 호출하지 않음으로써 메시지를 재처리 가능하도록 유지
        }
    }
}

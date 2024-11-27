package com.grepp.quizy.quiz.infra.user.kafka

import com.grepp.quizy.kafka.consumer.KafkaConsumer
import com.grepp.quizy.quiz.domain.user.UserUpdatedEvent
import com.grepp.quizy.quiz.domain.user.UserUpdater
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class UserUpdatedEventConsumer(
    private val userUpdater: UserUpdater
) : KafkaConsumer<Long, UserUpdatedEvent> {

    @KafkaListener(
        id = "\${kafka.topic.user}",
        topics = ["\${kafka.consumer-group.user}"]
    )
    override fun receive(
        records: List<ConsumerRecord<Long, UserUpdatedEvent>>
    ) {
        records.forEach {
            val event = it.value()
            userUpdater.update(event.toDomain())
        }
    }
}
package com.grepp.quizy.quiz.infra.user.kafka

import com.grepp.quizy.kafka.consumer.KafkaConsumer
import com.grepp.quizy.quiz.domain.user.UserAppender
import com.grepp.quizy.quiz.domain.user.UserCreatedEvent
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class UserCreatedEventConsumer(
    private val userAppender: UserAppender
) : KafkaConsumer<Long, UserCreatedEvent> {

    @KafkaListener(
        id = "\${kafka.topic.user}",
        topics = ["\${kafka.consumer-group.user}"]
    )
    override fun receive(
        records: List<ConsumerRecord<Long, UserCreatedEvent>>
    ) {
        records.forEach {
            val event = it.value()
            userAppender.append(event.toDomain())
        }
    }
}
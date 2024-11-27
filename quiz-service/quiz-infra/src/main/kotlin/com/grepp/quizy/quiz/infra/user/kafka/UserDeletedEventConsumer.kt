package com.grepp.quizy.quiz.infra.user.kafka

import com.grepp.quizy.kafka.consumer.KafkaConsumer
import com.grepp.quizy.quiz.domain.user.UserDeletedEvent
import com.grepp.quizy.quiz.domain.user.UserDeleter
import com.grepp.quizy.quiz.domain.user.UserId
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class UserDeletedEventConsumer(
    private val userDeleter: UserDeleter
) : KafkaConsumer<String, UserDeletedEvent> {

    @KafkaListener(
        id = "\${kafka.topic.user}",
        topics = ["\${kafka.consumer-group.user}"]
    )
    override fun receive(records: List<ConsumerRecord<String, UserDeletedEvent>>) {
        records.forEach {
            val event = it.value()
            userDeleter.delete(UserId(event.userId))
        }
    }
}
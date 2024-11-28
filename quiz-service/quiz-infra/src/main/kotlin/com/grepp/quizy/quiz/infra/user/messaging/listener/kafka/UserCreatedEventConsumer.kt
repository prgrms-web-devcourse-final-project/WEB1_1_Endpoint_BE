package com.grepp.quizy.quiz.infra.user.messaging.listener.kafka

import com.grepp.quizy.kafka.consumer.KafkaConsumer
import com.grepp.quizy.quiz.infra.user.repository.UserJpaRepository
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class UserCreatedEventConsumer(
    private val userJpaRepository: UserJpaRepository
) : KafkaConsumer<Long, UserCreatedEvent> {

    @KafkaListener(
        id = "user-created-event-consumer",
        topics = ["\${kafka.consumer-group.user}"]
    )
    override fun receive(
        records: List<ConsumerRecord<Long, UserCreatedEvent>>
    ) {
        records.forEach {
            val event = it.value()
            userJpaRepository.save(event.UserEntity())
        }
    }
}
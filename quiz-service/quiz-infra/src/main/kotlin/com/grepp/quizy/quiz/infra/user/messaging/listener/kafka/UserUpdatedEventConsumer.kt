package com.grepp.quizy.quiz.infra.user.messaging.listener.kafka

import com.grepp.quizy.kafka.consumer.KafkaConsumer
import com.grepp.quizy.quiz.infra.user.repository.UserJpaRepository
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class UserUpdatedEventConsumer(
    private val userJpaRepository: UserJpaRepository
) : KafkaConsumer<Long, UserUpdatedEvent> {

    @KafkaListener(
        id = "user-updated-event-consumer",
        topics = ["\${kafka.consumer-group.user}"]
    )
    override fun receive(
        records: List<ConsumerRecord<Long, UserUpdatedEvent>>
    ) {
        records.forEach {
            val event = it.value()
            userJpaRepository.findById(event.userId).ifPresent { entity ->
                entity.update(event.name, event.profileImageUrl)
                userJpaRepository.save(entity)
            }
        }
    }
}
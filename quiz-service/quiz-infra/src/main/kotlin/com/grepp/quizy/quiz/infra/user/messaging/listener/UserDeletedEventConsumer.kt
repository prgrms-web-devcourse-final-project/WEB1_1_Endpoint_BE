package com.grepp.quizy.quiz.infra.user.messaging.listener

import com.grepp.quizy.kafka.consumer.KafkaConsumer
import com.grepp.quizy.quiz.infra.user.repository.UserJpaRepository
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class UserDeletedEventConsumer(
    private val userJpaRepository: UserJpaRepository
) : KafkaConsumer<String, UserDeletedEvent> {

    @KafkaListener(
        groupId = "\${kafka.consumer-group.user}",
        topics = ["\${kafka.topic.user}"]
    )
    override fun receive(records: List<ConsumerRecord<String, UserDeletedEvent>>) {
        records.forEach {
            val event = it.value()
            userJpaRepository.findById(event.userId).ifPresent {
                userJpaRepository.deleteById(event.userId)
            }
        }
    }
}
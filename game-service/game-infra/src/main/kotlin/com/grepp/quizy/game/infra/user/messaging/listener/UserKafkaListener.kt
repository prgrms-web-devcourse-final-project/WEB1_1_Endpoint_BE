package com.grepp.quizy.game.infra.game.messaging.listener

import com.grepp.quizy.game.domain.user.User
import com.grepp.quizy.game.domain.user.UserCreatedEvent
import com.grepp.quizy.game.infra.user.repository.UserRepositoryAdaptor
import com.grepp.quizy.kafka.consumer.KafkaConsumer
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class UserKafkaListener(
    private val userRepository: UserRepositoryAdaptor
) : KafkaConsumer<Long, UserCreatedEvent> {

    @KafkaListener(
        groupId = "\${kafka.consumer-group.game}",
        topics = ["\${kafka.topic.user}"]
    )
    override fun receive(
        records: List<ConsumerRecord<Long, UserCreatedEvent>>
    ) {
        records.forEach {
            val userCreatedEvent = it.value()
            userRepository.save(User.from(userCreatedEvent))
        }
    }
}
package com.grepp.quizy.user.infra.user.messaging.listener

import com.grepp.quizy.kafka.consumer.KafkaConsumer
import com.grepp.quizy.user.domain.user.InterestOnboardingEvent
import com.grepp.quizy.user.domain.user.exception.CustomUserException
import com.grepp.quizy.user.infra.user.repository.UserRepositoryAdaptor
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class UserKafkaListener(
    private val userRepository: UserRepositoryAdaptor
) : KafkaConsumer<Long, InterestOnboardingEvent> {

    @KafkaListener(
        id = "\${kafka.topic.user}",
        topics = ["\${kafka.consumer-group.onboarding}"]
    )
    override fun receive(
        records: List<ConsumerRecord<Long, InterestOnboardingEvent>>
    ) {
        records.forEach {
            val interestOnboardingEvent = it.value()
            val user = userRepository.findById(interestOnboardingEvent.userId)
                ?: throw CustomUserException.UserNotFoundException
            user.changeRole()
            userRepository.save(user)
        }
    }
}
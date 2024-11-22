package com.grepp.quizy.user.infra.user.messaging.publisher

import com.grepp.quizy.kafka.producer.KafkaMessageHelper
import com.grepp.quizy.kafka.producer.KafkaProducer
import com.grepp.quizy.user.domain.user.UserEvent
import com.grepp.quizy.user.domain.user.UserMessageSender
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class UserKafkaMessagePublisher(
    private val userProducer: KafkaProducer<Long, UserEvent>,
    @Value("\${kafka.topic.user}") private val topic: String,
) : UserMessageSender {
    override fun send(message: UserEvent) {
        userProducer.send(
            topic,
            message.getUserId(),
            message,
            KafkaMessageHelper.getKafkaCallback(message),
        )
    }
}
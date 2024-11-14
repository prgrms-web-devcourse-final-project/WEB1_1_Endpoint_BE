package com.grepp.quizy.infra.quiz.messaging.publisher

import com.grepp.quizy.domain.quiz.QuizCreatedEvent
import com.grepp.quizy.domain.quiz.QuizMessagePublisher
import com.grepp.quizy.kafka.producer.KafkaMessageHelper
import com.grepp.quizy.kafka.producer.KafkaProducer
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class QuizKafkaPublisher(
        private val quizProducer: KafkaProducer<Long, QuizCreatedEvent>,
        @Value("\${kafka.topic.quiz}") private val topic: String,
) : QuizMessagePublisher {

    override fun publish(quizCreatedEvent: QuizCreatedEvent) {
        quizProducer.send(
                topic,
                quizCreatedEvent.id,
                quizCreatedEvent,
                KafkaMessageHelper.getKafkaCallback(quizCreatedEvent),
        )
    }
}

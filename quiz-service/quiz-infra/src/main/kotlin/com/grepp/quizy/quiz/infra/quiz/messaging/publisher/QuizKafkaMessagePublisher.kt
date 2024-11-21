package com.grepp.quizy.quiz.infra.quiz.messaging.publisher

import com.grepp.quizy.kafka.producer.KafkaMessageHelper
import com.grepp.quizy.kafka.producer.KafkaProducer
import com.grepp.quizy.quiz.domain.quiz.QuizEvent
import com.grepp.quizy.quiz.domain.quiz.QuizMessageSender
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class QuizKafkaMessagePublisher(
        private val quizProducer: KafkaProducer<Long, QuizEvent>,
        @Value("\${kafka.topic.quiz}") private val topic: String,
) : QuizMessageSender {
    override fun send(message: QuizEvent) {
        quizProducer.send(
                topic,
                message.getQuizId(),
                message,
                KafkaMessageHelper.getKafkaCallback(message),
        )
    }
}

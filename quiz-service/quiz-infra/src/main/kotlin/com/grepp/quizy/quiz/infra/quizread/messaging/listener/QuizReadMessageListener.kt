package com.grepp.quizy.quiz.infra.quizread.messaging.listener

import com.grepp.quizy.kafka.consumer.KafkaConsumer
import com.grepp.quizy.quiz.domain.quiz.QuizCreatedEvent
import com.grepp.quizy.quiz.infra.quizread.messaging.listener.cdc.QuizSynchronizer
import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.KafkaListeners
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

@Component
class QuizReadMessageListener(
    private val quizSynchronizer: QuizSynchronizer
) : KafkaConsumer<Long, QuizCreatedEvent> {

    @KafkaListeners(
        KafkaListener(topics = ["quiz"], groupId = "\${kafka.consumer-group.quiz}"),
    )
    override fun receive(records: List<ConsumerRecord<Long, QuizCreatedEvent>>) {
        log.info { "QuizReadMessageListener receive: ${records.size}" }
        quizSynchronizer.createQuiz(records.map { it.value() })
    }
}
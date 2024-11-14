package com.grepp.quizy.infra.quiz.messaging.listener

import com.grepp.quizy.domain.quiz.QuizCreatedEvent
import com.grepp.quizy.kafka.consumer.KafkaConsumer
import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

val log = KotlinLogging.logger {}

@Component
class QuizKafkaListener : KafkaConsumer<Long, QuizCreatedEvent> {

    @KafkaListener(
            id = "\${kafka.topic.quiz}",
            topics = ["\${kafka.consumer-group.quiz}"],
    )
    override fun receive(
            records: List<ConsumerRecord<Long, QuizCreatedEvent>>
    ) {
        log.info { "QuizKafkaListener receive: $records" }
    }
}

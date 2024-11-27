package com.grepp.quizy.quiz.infra.quizread.messaging.listener.cdc

import com.grepp.quizy.quiz.infra.debezium.DebeziumEvent
import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

@Component
class CDCEventListener(
    private val eventHandlerFactory: EventHandlerFactory
) {

    @KafkaListener(topics = ["\${kafka.topic.cdc_quizzes}", "\${kafka.topic.cdc_quiz_options}", "\${kafka.topic.cdc_quiz_tags}"] , groupId = "\${kafka.consumer-group.cdc_quiz}")
    fun receive(records: List<ConsumerRecord<String, DebeziumEvent>>) {
        val sortedRecords: List<ConsumerRecord<String, DebeziumEvent>> = records.stream()
            .filter { shouldProcessEvent(it.value().payload) }
            .sorted(Comparator.comparing { r -> r.value().payload.date })
            .toList()

        log.info { "${sortedRecords.size} 개의 이벤트를 처리 요청" }


        sortedRecords.forEach { record ->
            log.info {
                "${record.value().payload.operation} 이벤트를 ${record.topic()} 토픽에 처리 요청"
            }
            eventHandlerFactory.getHandler(record.topic()).process(record.value())
        }
    }

    private fun shouldProcessEvent(payload: DebeziumEvent.DebeziumEventPayload): Boolean {
        // READ 이벤트 아니고
        // quizzes 테이블의 CREATE 이벤트가 아닐 때만 처리
        return !isQuizzesCreateEvent(payload) && payload.operation != DebeziumEvent.DebeziumEventPayloadOperation.READ

    }

    private fun isQuizzesCreateEvent(payload: DebeziumEvent.DebeziumEventPayload): Boolean {
        val isQuizzesTable = payload.source["table"] == "quizzes"
        val isCreateOperation = payload.operation == DebeziumEvent.DebeziumEventPayloadOperation.CREATE
        return isQuizzesTable && isCreateOperation
    }
}
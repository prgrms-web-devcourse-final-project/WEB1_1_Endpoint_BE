package com.grepp.quizy.quiz.infra.quizread.messaging.listener

import com.grepp.quizy.quiz.infra.debezium.DebeziumEvent
import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

@Component
class CDCEventListener(
    private val CDCEventHandlerFactory: CDCEventHandlerFactory
) {

    @KafkaListener(topics = ["\${kafka.topic.cdc_events}"] , groupId = "\${kafka.consumer-group.cdc_events}")
    fun receive(records: List<ConsumerRecord<String, DebeziumEvent>>, acknowledgment: Acknowledgment) {
        val sortedRecords: List<ConsumerRecord<String, DebeziumEvent>> = records.stream()
            .filter { shouldProcessEvent(it.value().payload) }
            .sorted(Comparator.comparing { r -> r.value().payload.date })
            .toList()

        log.info { "${sortedRecords.size} 개의 이벤트를 처리 요청" }


        sortedRecords.forEach { record ->
            log.info {
                "${record.value().payload.operation} 이벤트를 ${record.topic()} 토픽에 처리 요청"
            }
            CDCEventHandlerFactory.getHandler(getTableName(record)).process(record.value())
        }
        acknowledgment.acknowledge()
    }

    private fun shouldProcessEvent(payload: DebeziumEvent.DebeziumEventPayload): Boolean {
        // READ 이벤트 처리하지 않음
        return payload.operation != DebeziumEvent.DebeziumEventPayloadOperation.READ
    }

    private fun getTableName(record: ConsumerRecord<String, DebeziumEvent>): String {
        return record.value().payload.source["table"] as String
    }
}
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
    private val cdcEventHandlerFactory: CDCEventHandlerFactory
) {

    @KafkaListener(topics = ["\${kafka.topic.cdc_events}"], groupId = "\${kafka.consumer-group.cdc_events}")
    fun receive(record: ConsumerRecord<String, DebeziumEvent>, acknowledgment: Acknowledgment) {
        val payload = record.value().payload

        if (isReadOperation(payload)) {
            log.info { "처리하지 않는 이벤트: ${payload.operation}" }
            acknowledgment.acknowledge()
            return
        }

        log.info { "${payload.source["table"]} - ${payload.operation} 이벤트를 ${record.topic()} 토픽에서 처리 요청" }

        try {
            val tableName = getTableName(record)
            cdcEventHandlerFactory.getHandler(tableName).process(record.value())
            acknowledgment.acknowledge()
        } catch (ex: Exception) {
            log.error(ex) { "이벤트 처리 중 오류 발생: ${record.value()}" }
        }
    }

    private fun isReadOperation(payload: DebeziumEvent.DebeziumEventPayload): Boolean {
        return payload.operation == DebeziumEvent.DebeziumEventPayloadOperation.READ
    }

    private fun getTableName(record: ConsumerRecord<String, DebeziumEvent>): String {
        return record.value().payload.source["table"] as String
    }
}
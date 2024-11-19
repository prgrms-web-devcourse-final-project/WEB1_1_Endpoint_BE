package com.grepp.quizy.kafka.producer

import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.concurrent.CompletableFuture
import org.apache.kafka.clients.producer.RecordMetadata
import org.springframework.kafka.support.SendResult

private val log = KotlinLogging.logger {}

object KafkaMessageHelper {

    fun <K, V> getKafkaCallback(
            payload: V
    ): CompletableFuture<SendResult<K, V>> {
        return object :
                CompletableFuture<SendResult<K, V>>() {
            override fun complete(
                    value: SendResult<K, V>
            ): Boolean {
                val metadata: RecordMetadata =
                        value.recordMetadata
                log.info {
                    "${"Kafka message sent successfully: Topic: {} Partition: {} Offset: {} Timestamp: {}"} ${
                        arrayOf<Any>(
                            metadata.topic(),
                            metadata.partition(),
                            metadata.offset(),
                            metadata.timestamp(),
                        )
                    }"
                }
                return super.complete(value)
            }

            override fun completeExceptionally(
                    ex: Throwable
            ): Boolean {
                log.error {
                    "${"Error while sending Kafka message: {}"} ${payload.toString()} $ex"
                }
                return super.completeExceptionally(ex)
            }
        }
    }
}

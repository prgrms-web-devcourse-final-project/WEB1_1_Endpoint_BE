package com.grepp.quizy.kafka.producer

import jakarta.annotation.PreDestroy
import java.io.Serializable
import java.util.concurrent.CompletableFuture
import org.slf4j.LoggerFactory
import org.springframework.kafka.KafkaException
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component

@Component
class KafkaProducerImpl<K : Serializable, V : Serializable>(
        private val kafkaTemplate: KafkaTemplate<K, V>
) : KafkaProducer<K, V> {

    private val log =
            LoggerFactory.getLogger(this::class.java)

    override fun send(
            topicName: String,
            key: K,
            message: V,
            callback: CompletableFuture<SendResult<K, V>>,
    ) {
        log.info(
                "Sending message={} to topic={}",
                message.javaClass.getSimpleName(),
                topicName,
        )

        try {
            val kafkaResultFuture =
                    kafkaTemplate.send(
                            topicName,
                            key,
                            message,
                    )
            kafkaResultFuture.whenComplete {
                    result: SendResult<K, V>,
                    ex: Throwable? ->
                if (ex != null) {
                    callback.completeExceptionally(ex)
                } else {
                    callback.complete(result)
                }
            }
        } catch (e: KafkaException) {
            log.error(
                    "Error on kafka producer with key: {}, message: {} and exception: {}",
                    key,
                    message,
                    e.message,
            )
            throw RuntimeException(
                    "Error on kafka producer with key: $key and message: $message"
            )
        }
    }

    @PreDestroy
    fun close() {
        log.info("Closing kafka producer!")
        kafkaTemplate.destroy()
    }
}

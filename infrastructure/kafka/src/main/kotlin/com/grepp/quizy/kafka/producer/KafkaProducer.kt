package com.grepp.quizy.kafka.producer

import java.io.Serializable
import java.util.concurrent.CompletableFuture
import org.springframework.kafka.support.SendResult

interface KafkaProducer<K : Serializable, V : Serializable> {
    fun send(
            topicName: String,
            key: K,
            message: V,
            callback: CompletableFuture<SendResult<K, V>>,
    )
}

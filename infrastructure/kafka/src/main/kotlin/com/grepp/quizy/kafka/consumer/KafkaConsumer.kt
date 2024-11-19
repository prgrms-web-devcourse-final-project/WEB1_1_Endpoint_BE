package com.grepp.quizy.kafka.consumer

import java.io.Serializable
import org.apache.kafka.clients.consumer.ConsumerRecord

interface KafkaConsumer<K : Serializable, T : Serializable> {
    fun receive(records: List<ConsumerRecord<K, T>>)
}

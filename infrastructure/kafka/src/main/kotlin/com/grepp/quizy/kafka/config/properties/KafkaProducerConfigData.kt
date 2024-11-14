package com.grepp.quizy.kafka.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

const val PRODUCER_CONFIG = "kafka-producer-config"

@ConfigurationProperties(prefix = PRODUCER_CONFIG)
data class KafkaProducerConfigData(
        val keySerializerClass: String,
        val valueSerializerClass: String,
        val compressionType: String,
        val acks: String,
        val batchSize: Int,
        val batchSizeBoostFactor: Int,
        val lingerMs: Int,
        val requestTimeoutMs: Int,
        val retryCount: Int,
)

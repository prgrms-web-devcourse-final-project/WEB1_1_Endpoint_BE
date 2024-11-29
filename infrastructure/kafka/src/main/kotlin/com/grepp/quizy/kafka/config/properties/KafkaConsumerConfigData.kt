package com.grepp.quizy.kafka.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

const val CONSUMER_CONFIG = "kafka-consumer-config"

@ConfigurationProperties(prefix = CONSUMER_CONFIG)
class KafkaConsumerConfigData(
        val keyDeserializer: String,
        val valueDeserializer: String,
        val autoOffsetReset: String,
        val batchListener: Boolean,
        val autoStartup: Boolean,
        val concurrencyLevel: Int,
        val sessionTimeoutMs: Int,
        val heartbeatIntervalMs: Int,
        val maxPollIntervalMs: Int,
        val pollTimeoutMs: Long,
        val maxPollRecords: Int,
        val maxPartitionFetchBytesDefault: Int,
        val maxPartitionFetchBytesBoostFactor: Int,
        // 옵셔널 필드 추가
        val useTypeInfoHeaders: Boolean? = null,
        val typeMappings: String? = null,
        val defaultType: String? = null,
        val ackMode: String? = null
)

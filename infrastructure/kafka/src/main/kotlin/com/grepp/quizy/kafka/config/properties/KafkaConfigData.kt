package com.grepp.quizy.kafka.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

const val CONFIG_DATA = "kafka-config"

@ConfigurationProperties(prefix = CONFIG_DATA)
class KafkaConfigData(
        val bootstrapServers: String,
        val numOfPartitions: Int,
        val replicationFactor: Short,
)

package com.grepp.quizy.kafka

import com.grepp.quizy.kafka.config.properties.KafkaConfigData
import com.grepp.quizy.kafka.config.properties.KafkaConsumerConfigData
import com.grepp.quizy.kafka.config.properties.KafkaProducerConfigData
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties

@EnableConfigurationProperties(
        KafkaConfigData::class,
        KafkaProducerConfigData::class,
        KafkaConsumerConfigData::class,
)
@SpringBootApplication(
        scanBasePackages = ["com.grepp.quizy.kafka"]
)
class KafkaApplication

fun main(args: Array<String>) {}

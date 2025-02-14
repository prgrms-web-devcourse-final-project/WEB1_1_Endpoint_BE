package com.grepp.quizy.kafka.config

import com.grepp.quizy.kafka.config.properties.KafkaConfigData
import com.grepp.quizy.kafka.config.properties.KafkaConsumerConfigData
import java.io.Serializable
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.support.serializer.JsonDeserializer

@Configuration
class KafkaConsumerConfig<K : Serializable, V : Serializable>(
        private val kafkaConfigData: KafkaConfigData,
        private val kafkaConsumerConfigData: KafkaConsumerConfigData,
) {
    @Bean
    fun consumerConfigs(): Map<String, Any> {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] =
                kafkaConfigData.bootstrapServers
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] =
                kafkaConsumerConfigData.keyDeserializer
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] =
                kafkaConsumerConfigData.valueDeserializer
        props[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] =
                kafkaConsumerConfigData.autoOffsetReset
        props[ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG] =
                kafkaConsumerConfigData.sessionTimeoutMs
        props[ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG] =
                kafkaConsumerConfigData.heartbeatIntervalMs
        props[ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG] =
                kafkaConsumerConfigData.maxPollIntervalMs
        props[ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG] =
                (kafkaConsumerConfigData
                        .maxPartitionFetchBytesDefault *
                        kafkaConsumerConfigData
                                .maxPartitionFetchBytesBoostFactor)
        props[ConsumerConfig.MAX_POLL_RECORDS_CONFIG] =
                kafkaConsumerConfigData.maxPollRecords
        props[JsonDeserializer.USE_TYPE_INFO_HEADERS] = "true"
        props[JsonDeserializer.TRUSTED_PACKAGES] = "*"

        // 옵셔널 설정들 조건부 추가
        kafkaConsumerConfigData.useTypeInfoHeaders?.let {
            props[JsonDeserializer.USE_TYPE_INFO_HEADERS] = it.toString()
        }

        kafkaConsumerConfigData.typeMappings?.let { mappings ->
            props[JsonDeserializer.TYPE_MAPPINGS] = mappings.joinToString(separator = ",")
        }

        kafkaConsumerConfigData.defaultType?.let {
            props[JsonDeserializer.VALUE_DEFAULT_TYPE] = it
        }

        return props
    }

    @Bean
    fun consumerFactory(): ConsumerFactory<K, V> {
        return DefaultKafkaConsumerFactory(consumerConfigs())
    }

    @Bean
    fun kafkaListenerContainerFactory():
            KafkaListenerContainerFactory<
                    ConcurrentMessageListenerContainer<K, V>
            > {
        val factory = ConcurrentKafkaListenerContainerFactory<K, V>()
        factory.consumerFactory = consumerFactory()
        factory.isBatchListener =
                kafkaConsumerConfigData.batchListener
        kafkaConsumerConfigData.ackMode?.let {
            factory.containerProperties.ackMode =
                    ContainerProperties.AckMode.valueOf(it)
        }
        factory.setConcurrency(
                kafkaConsumerConfigData.concurrencyLevel
        )
        factory.setAutoStartup(kafkaConsumerConfigData.autoStartup)
        factory.containerProperties.pollTimeout =
                kafkaConsumerConfigData.pollTimeoutMs
        return factory
    }
}

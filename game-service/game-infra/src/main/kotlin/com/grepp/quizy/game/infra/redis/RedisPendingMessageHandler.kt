package com.grepp.quizy.game.infra.redis

import com.grepp.quizy.game.infra.redis.util.RedisOperator
import org.slf4j.LoggerFactory
import org.springframework.data.redis.connection.stream.PendingMessage
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class RedisPendingMessageHandler(
    private val redisOperator: RedisOperator
) : PendingMessageProcessor {
    private val log = LoggerFactory.getLogger(this::class.java)
    private val consumers = mutableListOf<ConsumerConfig>()

    data class ConsumerConfig(
        val streamKey: String,
        val consumerGroupName: String,
        val consumerName: String,
        val messageProcessor: RedisMessageProcessor
    )

    fun registerConsumer(
        streamKey: String,
        consumerGroupName: String,
        consumerName: String,
        processor: RedisMessageProcessor
    ) {
        consumers.add(
            ConsumerConfig(
                streamKey = streamKey,
                consumerGroupName = consumerGroupName,
                consumerName = consumerName,
                messageProcessor = processor
            )
        )
    }

    @Scheduled(fixedRate = 10000)
    override fun processPendingMessages() {
        consumers.forEach { config ->
            processPendingMessagesForConsumer(config)
        }
    }

    private fun processPendingMessagesForConsumer(config: ConsumerConfig) {
        val pendingMessages = redisOperator.findStreamPendingMessages(
            config.streamKey,
            config.consumerGroupName,
            config.consumerName
        )

        pendingMessages.forEach { pendingMessage ->
            processPendingMessage(pendingMessage, config)
        }
    }

    private fun processPendingMessage(pendingMessage: PendingMessage, config: ConsumerConfig) {
        try {
            redisOperator.claimStream(pendingMessage, config.consumerName)

            val messageToProcess = redisOperator.findStreamMessageById(
                config.streamKey,
                pendingMessage.getIdAsString()
            )

            when {
                messageToProcess == null -> {
                    log.info("메시지가 존재하지 않습니다: ${pendingMessage.getIdAsString()}")
                    return
                }

                shouldSkipProcessing(pendingMessage) -> {
                    handleSkippedMessage(pendingMessage)
                    redisOperator.ackStream(config.consumerGroupName, messageToProcess)
                    return
                }

                else -> {
                    config.messageProcessor.processMessage(messageToProcess)
                    redisOperator.ackStream(config.consumerGroupName, messageToProcess)
                }
            }
        } catch (e: Exception) {
            handlePendingMessageError(pendingMessage, e)
        }
    }

    private fun shouldSkipProcessing(pendingMessage: PendingMessage): Boolean {
        val errorCount = redisOperator.getRedisValue("errorCount", pendingMessage.getIdAsString()) as? Int ?: 0
        return errorCount >= 5 || pendingMessage.getTotalDeliveryCount() >= 2
    }

    private fun handleSkippedMessage(pendingMessage: PendingMessage) {
        val errorCount = redisOperator.getRedisValue("errorCount", pendingMessage.getIdAsString()) as? Int ?: 0
        when {
            errorCount >= 5 -> log.info("재처리 최대 시도 횟수 초과: ${pendingMessage.getIdAsString()}")
            pendingMessage.getTotalDeliveryCount() >= 2 -> log.info("최대 delivery 횟수 초과: ${pendingMessage.getIdAsString()}")
        }
    }

    private fun handlePendingMessageError(pendingMessage: PendingMessage, e: Exception) {
        log.error("Pending 메시지 처리 중 에러 발생: ${pendingMessage.getIdAsString()}", e)
        redisOperator.increaseRedisValue("errorCount", pendingMessage.getIdAsString())
    }

}
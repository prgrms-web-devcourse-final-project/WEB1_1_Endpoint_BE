package com.grepp.quizy.game.infra.redis

import com.grepp.quizy.game.infra.redis.util.RedisOperator
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean
import org.springframework.data.redis.connection.stream.Consumer
import org.springframework.data.redis.connection.stream.MapRecord
import org.springframework.data.redis.connection.stream.ReadOffset
import org.springframework.data.redis.connection.stream.StreamOffset
import org.springframework.data.redis.stream.StreamListener
import org.springframework.data.redis.stream.StreamMessageListenerContainer
import org.springframework.data.redis.stream.Subscription

// 기본 스트림 리스너 인터페이스
interface RedisMessageProcessor {
    fun processMessage(message: MapRecord<String?, Any?, Any?>?)
}

// Pending 메시지 처리를 위한 인터페이스
interface PendingMessageProcessor {
    fun processPendingMessages()
}

// 기본 스트림 리스너 구현체
abstract class AbstractRedisStreamConsumer protected constructor(
    val redisOperator: RedisOperator,
    var streamKey: String,
    var consumerGroupName: String,
    var consumerName: String
) : StreamListener<String, MapRecord<String?, Any?, Any?>>, InitializingBean,
    DisposableBean, RedisMessageProcessor {

    private val log = LoggerFactory.getLogger(this::class.java)

    private var listenerContainer: StreamMessageListenerContainer<String, MapRecord<String?, Any?, Any?>>? = null
    private var subscription: Subscription? = null

    override fun onMessage(message: MapRecord<String?, Any?, Any?>) {
        try {
            processMessage(message)
            redisOperator.ackStream(consumerGroupName, message)
        } catch (e: Exception) {
            handleError(message, e)
        }
    }

    private fun handleError(message: MapRecord<String?, Any?, Any?>, e: Exception?) {
        log.error("Failed to process message: ${message.id}, error: ${e?.message}", e)
        // 에러 카운트 증가 등 추가 처리
        redisOperator.increaseRedisValue("errorCount", message.id.toString())
        throw RuntimeException("Failed to process message: $message", e)
    }

    override fun destroy() {
        if (this.subscription != null) {
            subscription!!.cancel()
        }
        if (this.listenerContainer != null) {
            listenerContainer!!.stop()
        }
    }

    override fun afterPropertiesSet() {
        try {
            initializeStreamConsumer()
        } catch (e: Exception) {
            // BUSYGROUP Consumer Group already exists 에러 처리
            if (e.message?.contains("BUSYGROUP") == true) {
                log.warn("Consumer group already exists for stream: $streamKey")
                // 기존 그룹에 대해 리스너 설정 진행
                setupListener()
            } else {
                throw e
            }
        }
    }

    protected fun initializeStreamConsumer() {
        redisOperator.createStreamConsumerGroup(streamKey, consumerGroupName)
        setupListener()
    }

    private fun setupListener() {
        this.listenerContainer = redisOperator.createStreamMessageListenerContainer() as StreamMessageListenerContainer<String, MapRecord<String?, Any?, Any?>>?

        this.subscription = listenerContainer?.receive(
            Consumer.from(this.consumerGroupName, consumerName),
            StreamOffset.create(streamKey, readOffset),
            this
        )

        listenerContainer?.start()
    }

    protected val readOffset: ReadOffset
        get() = ReadOffset.lastConsumed()
}



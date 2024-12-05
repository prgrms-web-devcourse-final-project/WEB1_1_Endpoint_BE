package com.grepp.quizy.game.infra.redis

import com.grepp.quizy.game.infra.redis.util.RedisOperator
import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean
import org.springframework.data.redis.connection.stream.Consumer
import org.springframework.data.redis.connection.stream.MapRecord
import org.springframework.data.redis.connection.stream.ReadOffset
import org.springframework.data.redis.connection.stream.StreamOffset
import org.springframework.data.redis.stream.StreamListener
import org.springframework.data.redis.stream.StreamMessageListenerContainer
import org.springframework.data.redis.stream.Subscription

abstract class AbstractRedisStreamConsumer protected constructor(
    val redisOperator: RedisOperator,
    var streamKey: String,
    var consumerGroupName: String,
    var consumerName: String
) : StreamListener<String, MapRecord<String?, Any?, Any?>>, InitializingBean,
    DisposableBean {
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

    protected abstract fun processMessage(message: MapRecord<String?, Any?, Any?>?)

    private fun handleError(message: MapRecord<String?, Any?, Any?>, e: Exception?) {
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
        initializeStreamConsumer()
    }

    protected fun initializeStreamConsumer() {
        // Stream 기본 설정
        redisOperator.createStreamConsumerGroup(streamKey, consumerGroupName)

        // StreamMessageListenerContainer 설정
        this.listenerContainer =
            redisOperator.createStreamMessageListenerContainer() as StreamMessageListenerContainer<String, MapRecord<String?, Any?, Any?>>?

        // 구독 설정
        this.subscription = listenerContainer?.receive(
            Consumer.from(this.consumerGroupName, consumerName),
            StreamOffset.create(streamKey, readOffset),
            this
        )

        // 레디스 listen 시작
        listenerContainer?.start()
    }

    protected val readOffset: ReadOffset
        get() =
            ReadOffset.lastConsumed()
}



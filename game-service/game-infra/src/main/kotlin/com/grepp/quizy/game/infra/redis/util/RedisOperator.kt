package com.grepp.quizy.game.infra.redis.util

import com.grepp.quizy.game.domain.message.StreamMessage
import io.lettuce.core.api.async.RedisAsyncCommands
import io.lettuce.core.codec.StringCodec
import io.lettuce.core.output.StatusOutput
import io.lettuce.core.protocol.CommandArgs
import io.lettuce.core.protocol.CommandKeyword
import io.lettuce.core.protocol.CommandType
import org.springframework.data.domain.Range
import org.springframework.data.redis.connection.stream.*
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.data.redis.stream.StreamMessageListenerContainer
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RedisOperator(
    private val redisTemplate: RedisTemplate<String, String>
) {
    fun sendStreamMessage(streamKey: String, message: StreamMessage): String {
        return redisTemplate.opsForStream<Any, Any>()
            .add(
                StreamRecords.newRecord()
                    .`in`(streamKey)
                    .ofMap(message.value)
            )
            .value
    }

    fun getRedisValue(key: String, field: String): Any? {
        return redisTemplate.opsForHash<Any, Any>()[key, field]
    }

    fun increaseRedisValue(key: String, field: String): Long {
        return redisTemplate.opsForHash<Any, Any>().increment(key, field, 1)
    }

    fun ackStream(consumerGroupName: String, message: MapRecord<String?, Any?, Any?>) {
        redisTemplate.opsForStream<Any, Any>().acknowledge(consumerGroupName, message)
    }

    fun claimStream(pendingMessage: PendingMessage, consumerName: String?) {
        val commands = redisTemplate
            .connectionFactory.connection.nativeConnection as RedisAsyncCommands<String, String>

        val args = CommandArgs(StringCodec.UTF8)
            .add(pendingMessage.idAsString)
            .add(pendingMessage.groupName)
            .add(consumerName)
            .add("20")
            .add(pendingMessage.idAsString)
        commands.dispatch(CommandType.XCLAIM, StatusOutput(StringCodec.UTF8), args)
    }

    fun findStreamMessageById(streamKey: String, id: String): MapRecord<String?, Any?, Any?>? {
        val mapRecordList = this.findStreamMessageByRange(streamKey, id, id)
        if (mapRecordList!!.isEmpty()) return null
        return mapRecordList[0]
    }

    fun findStreamMessageByRange(
        streamKey: String,
        startId: String,
        endId: String
    ): List<MapRecord<String?, Any?, Any?>>? {
        return redisTemplate.opsForStream<Any, Any>().range(streamKey, Range.closed(startId, endId))
    }

    fun createStreamConsumerGroup(streamKey: String, consumerGroupName: String) {
        // if stream is not exist, create stream and consumer group of it
        if (java.lang.Boolean.FALSE == redisTemplate.hasKey(streamKey)) {
            val commands = redisTemplate
                .connectionFactory
                .connection
                .nativeConnection as RedisAsyncCommands<String, String>

            val args = CommandArgs(StringCodec.UTF8)
                .add(CommandKeyword.CREATE)
                .add(streamKey)
                .add(consumerGroupName)
                .add("0")
                .add("MKSTREAM")

            commands.dispatch(CommandType.XGROUP, StatusOutput(StringCodec.UTF8), args)
        } else {
            if (!isStreamConsumerGroupExist(streamKey, consumerGroupName)) {
                redisTemplate.opsForStream<Any, Any>().createGroup(streamKey, ReadOffset.from("0"), consumerGroupName)
            }
        }
    }

    fun findStreamPendingMessages(streamKey: String, consumerGroupName: String, consumerName: String): PendingMessages {
        return redisTemplate.opsForStream<Any, Any>()
            .pending(streamKey, Consumer.from(consumerGroupName, consumerName), Range.unbounded<Any>(), 100L)
    }

    fun isStreamConsumerGroupExist(streamKey: String, consumerGroupName: String): Boolean {
        val iterator = redisTemplate
            .opsForStream<Any, Any>().groups(streamKey).stream().iterator()

        while (iterator.hasNext()) {
            val xInfoGroup = iterator.next()
            if (xInfoGroup.groupName() == consumerGroupName) {
                return true
            }
        }
        return false
    }

    fun createStreamMessageListenerContainer(): StreamMessageListenerContainer<*, *> {
        return StreamMessageListenerContainer.create(
            redisTemplate.connectionFactory,
            StreamMessageListenerContainer
                .StreamMessageListenerContainerOptions.builder()
                .hashKeySerializer<String, Any>(StringRedisSerializer())
                .hashValueSerializer<Any, String>(StringRedisSerializer())
                .pollTimeout(Duration.ofSeconds(1))
                .build()
        )
    }
}
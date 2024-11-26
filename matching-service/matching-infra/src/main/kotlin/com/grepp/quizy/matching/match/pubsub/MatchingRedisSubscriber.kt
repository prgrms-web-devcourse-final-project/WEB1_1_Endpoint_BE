package com.grepp.quizy.matching.match.pubsub

import com.fasterxml.jackson.databind.ObjectMapper
import com.grepp.quizy.matching.match.MatchingEventSender
import com.grepp.quizy.matching.match.PersonalMatchingSucceedEvent
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.stereotype.Component

@Component
class MatchingRedisSubscriber(
    private val matchingEventSender: MatchingEventSender,
    private val objectMapper: ObjectMapper,
) : MessageListener {

    override fun onMessage(message: Message, pattern: ByteArray?) {
        try {
            val event = objectMapper.readValue(
                message.body,
                PersonalMatchingSucceedEvent::class.java
            )

            matchingEventSender.send(event)
        } catch (e: Exception) {
            throw IllegalArgumentException("Failed to send event to Redis", e.cause)
        }
    }
}
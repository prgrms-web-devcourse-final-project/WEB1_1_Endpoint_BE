package com.grepp.quizy.matching.infra.match.pubsub

import com.fasterxml.jackson.databind.ObjectMapper
import com.grepp.quizy.matching.domain.match.MatchingEventSender
import com.grepp.quizy.matching.domain.match.PersonalMatchingSucceedEvent
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.stereotype.Component

@Component
class MatchingRedisSubscriber(
    private val matchingEventSender: MatchingEventSender,
    private val objectMapper: ObjectMapper,
) : MessageListener {

    private val log = KotlinLogging.logger {}

    override fun onMessage(message: Message, pattern: ByteArray?) {
        try {
            val event = objectMapper.readValue(
                message.body,
                PersonalMatchingSucceedEvent::class.java
            )

            log.info { "Matching succeeded event: $event" }
            matchingEventSender.send(event)
        } catch (e: Exception) {
            log.error(e) { "Matching failed" }
            throw IllegalArgumentException("Failed to send event to Redis", e.cause)
        }
    }
}
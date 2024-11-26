package com.grepp.quizy.matching.match.pubsub

import com.grepp.quizy.matching.match.MatchingEventPublisher
import com.grepp.quizy.matching.match.PersonalMatchingSucceedEvent
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Component

@Component
class MatchingRedisPublisher(
    private val matchingMessageTemplate: RedisTemplate<String, PersonalMatchingSucceedEvent>,
) : MatchingEventPublisher {

    override fun publish(event: PersonalMatchingSucceedEvent) {
        val topic = ChannelTopic("matching")
        matchingMessageTemplate.convertAndSend(topic.topic, event)
    }
}
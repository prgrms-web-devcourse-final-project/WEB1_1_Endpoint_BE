package com.grepp.quizy.matching.api.sse

import com.fasterxml.jackson.databind.ObjectMapper
import com.grepp.quizy.matching.domain.match.MatchingEventSender
import com.grepp.quizy.matching.domain.match.MatchingPoolManager
import com.grepp.quizy.matching.domain.match.PersonalMatchingSucceedEvent
import com.grepp.quizy.matching.domain.user.UserId
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException

@Component
class SseSender(
    private val matchingPoolManager: MatchingPoolManager,
    private val emitterRepository: SseEmitterRepository,
    private val objectMapper: ObjectMapper
) : MatchingEventSender {

    private val log = KotlinLogging.logger {}

    override fun send(event: PersonalMatchingSucceedEvent) {
        val emitter = emitterRepository.findById(event.userId) ?: return

        try {
            emitter.send(
                SseEmitter.event()
                    .id("")
                    .name("MATCHING")
                    .data(objectMapper.writeValueAsString(MatchingSucceed(event.gameRoomId)))
            )
            log.info { "SSE event sent to ${event.userId}" }
            removeFromPool(event.userId)
            emitter.complete()
        } catch (e: IOException) {
            removeFromPool(event.userId)
            emitter.completeWithError(e)
        }
    }

    private fun removeFromPool(userId: Long) {
        emitterRepository.remove(userId)
        matchingPoolManager.remove(UserId(userId))
    }
}

private data class MatchingSucceed(val roomId: Long)
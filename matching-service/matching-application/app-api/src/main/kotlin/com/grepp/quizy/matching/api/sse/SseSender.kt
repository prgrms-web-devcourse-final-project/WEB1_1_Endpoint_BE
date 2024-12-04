package com.grepp.quizy.matching.api.sse

import com.fasterxml.jackson.databind.ObjectMapper
import com.grepp.quizy.matching.domain.match.MatchingEventSender
import com.grepp.quizy.matching.domain.match.MatchingPoolManager
import com.grepp.quizy.matching.domain.match.PersonalMatchingSucceedEvent
import com.grepp.quizy.matching.domain.user.UserId
import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException

@Component
class SseSender(
    private val matchingPoolManager: MatchingPoolManager,
    private val emitterRepository: SseEmitterRepository,
    private val objectMapper: ObjectMapper
) : MatchingEventSender {

    override fun send(event: PersonalMatchingSucceedEvent) {
        val emitter = emitterRepository.findById(event.userId) ?: return

        try {
            emitter.send(
                SseEmitter.event()
                    .id("")
                    .name("MATCHING")
                    .data(objectMapper.writeValueAsString(MatchingSucceed(event.gameRoomId)))
            )
        } catch (e: IOException) {
            closeEmitter(event.userId)
            emitter.completeWithError(e)
        }
    }

    private fun closeEmitter(userId: Long) {
        emitterRepository.findById(userId)?.complete()
        emitterRepository.remove(userId)
        matchingPoolManager.remove(UserId(userId))
    }
}

private data class MatchingSucceed(val roomId: Long)
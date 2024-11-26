package com.grepp.quizy.matching.api.sse

import com.fasterxml.jackson.databind.ObjectMapper
import com.grepp.quizy.matching.match.MatchingEventSender
import com.grepp.quizy.matching.match.MatchingPoolManager
import com.grepp.quizy.matching.match.PersonalMatchingSucceedEvent
import com.grepp.quizy.matching.user.UserId
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
                    .data(objectMapper.writeValueAsString(event))
            )
        } catch (e: IOException) {
            closeEmitter(event.userId)
            emitter.completeWithError(e)
        }
    }

    private fun closeEmitter(userId: UserId) {
        emitterRepository.remove(userId)
        matchingPoolManager.remove(userId)
    }
}
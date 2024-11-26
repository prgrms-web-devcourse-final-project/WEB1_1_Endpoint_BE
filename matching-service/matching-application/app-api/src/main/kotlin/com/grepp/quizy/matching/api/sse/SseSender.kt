package com.grepp.quizy.matching.api.sse

import com.fasterxml.jackson.databind.ObjectMapper
import com.grepp.quizy.matching.match.MatchingEventSender
import com.grepp.quizy.matching.match.PersonalMatchingSucceedEvent
import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException

@Component
class SseSender(
    private val sseEmitterRepository: SseEmitterRepository,
    private val objectMapper: ObjectMapper
) : MatchingEventSender {

    override fun send(event: PersonalMatchingSucceedEvent) {
        val emitter = sseEmitterRepository.findById(event.userId) ?: return

        try {
            emitter.send(
                SseEmitter.event()
                    .id("")
                    .name("MATCHING")
                    .data(objectMapper.writeValueAsString(event))
            )
        } catch (e: IOException) {
            sseEmitterRepository.remove(event.userId)
            emitter.completeWithError(e)
        }
    }
}
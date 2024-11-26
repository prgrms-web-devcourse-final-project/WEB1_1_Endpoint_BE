package com.grepp.quizy.matching.api.sse

import com.grepp.quizy.matching.match.MatchingPoolManager
import com.grepp.quizy.matching.user.UserId
import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException

@Component
class SseConnector(
    private val matchingPoolManager: MatchingPoolManager,
    private val emitterRepository: SseEmitterRepository
) {
    
    fun connect(userId: UserId): SseEmitter {
        val emitter = SseEmitter(TIME_OUT)
        emitterRepository.save(userId, emitter)
        emitter.onTimeout { closeEmitter(userId) }
        emitter.onCompletion { closeEmitter(userId) }
        
        try {
            emitter.send(
                SseEmitter.event()
                    .id("")
                    .name(CONNECTION_NAME)
                    .data("emitter connected")
            )
        } catch (e: IOException) {
            emitterRepository.remove(userId)
            emitter.completeWithError(e)
        }
        
        return emitter
    }

    private fun closeEmitter(userId: UserId) {
        emitterRepository.remove(userId)
        matchingPoolManager.remove(userId)
    }
    
    companion object {
        const val CONNECTION_NAME = "CONNECT"
        const val TIME_OUT = 5 * 60 * 1000L
    }
}
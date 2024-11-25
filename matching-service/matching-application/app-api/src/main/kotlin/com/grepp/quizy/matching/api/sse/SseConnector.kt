package com.grepp.quizy.matching.api.sse

import com.grepp.quizy.matching.user.UserId
import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException

@Component
class SseConnector(private val emitterRepository: SseEmitterRepository) {
    
    fun connect(userId: UserId): SseEmitter {
        val emitter = SseEmitter(TIME_OUT)
        emitterRepository.save(userId, emitter)
        emitter.onTimeout { emitterRepository.remove(userId) }
        emitter.onCompletion { emitterRepository.remove(userId) }
        
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
    
    companion object {
        const val CONNECTION_NAME = "CONNECT"
        const val TIME_OUT = 5 * 60 * 1000L
    }
}
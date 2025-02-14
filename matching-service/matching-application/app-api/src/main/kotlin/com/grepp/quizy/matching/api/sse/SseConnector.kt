package com.grepp.quizy.matching.api.sse

import com.grepp.quizy.matching.domain.match.MatchingPoolManager
import com.grepp.quizy.matching.domain.user.UserId
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
        emitterRepository.save(userId.value, emitter)
        emitter.onTimeout { removeFromPool(userId) }
        emitter.onCompletion { removeFromPool(userId) }
        
        try {
            emitter.send(
                SseEmitter.event()
                    .id("")
                    .name(CONNECTION_NAME)
                    .data("emitter connected")
            )
        } catch (e: IOException) {
            emitterRepository.remove(userId.value)
            emitter.completeWithError(e)
        }
        
        return emitter
    }

    fun disconnect(userId: UserId) {
        emitterRepository.findById(userId.value)?.complete()
        removeFromPool(userId)
    }

    private fun removeFromPool(userId: UserId) {
        emitterRepository.remove(userId.value)
        matchingPoolManager.remove(userId)
    }
    
    companion object {
        const val CONNECTION_NAME = "CONNECT"
        const val TIME_OUT = 45 * 1000L
    }
}
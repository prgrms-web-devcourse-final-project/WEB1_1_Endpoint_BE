package com.grepp.quizy.matching.api.sse

import com.grepp.quizy.matching.user.UserId
import org.springframework.stereotype.Repository
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.concurrent.ConcurrentHashMap

@Repository
class SseEmitterRepository {
    private val emitters = ConcurrentHashMap<Long, SseEmitter>()

    fun save(userId: UserId, emitter: SseEmitter) {
        emitters[userId.value] = emitter
    }

    fun findById(userId: UserId) = emitters[userId.value]

    fun remove(userId: UserId) {
        emitters.remove(userId.value)
    }
}
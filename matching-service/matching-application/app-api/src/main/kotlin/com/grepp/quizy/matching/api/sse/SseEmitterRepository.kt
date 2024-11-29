package com.grepp.quizy.matching.api.sse

import org.springframework.stereotype.Repository
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.concurrent.ConcurrentHashMap

@Repository
class SseEmitterRepository {
    private val emitters = ConcurrentHashMap<Long, SseEmitter>()

    fun save(userId: Long, emitter: SseEmitter) {
        emitters[userId] = emitter
    }

    fun findById(userId: Long) = emitters[userId]

    fun remove(userId: Long) {
        emitters.remove(userId)
    }
}
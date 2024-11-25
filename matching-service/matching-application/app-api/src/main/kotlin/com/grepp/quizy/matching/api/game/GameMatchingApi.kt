package com.grepp.quizy.matching.api.game

import com.grepp.quizy.matching.api.sse.SseConnector
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@RestController
@RequestMapping("/api/matching")
class GameMatchingApi(private val sseConnector: SseConnector) {

    @GetMapping(
        value = "/subscribe",
        produces = [MediaType.TEXT_EVENT_STREAM_VALUE]
    )
    fun subscribe(@RequestHeader("X-Auth-Id") userId: UserId): ResponseEntity<SseEmitter> {
        val emitter = sseConnector.connect(userId)
        return ResponseEntity.ok(emitter)
    }
}
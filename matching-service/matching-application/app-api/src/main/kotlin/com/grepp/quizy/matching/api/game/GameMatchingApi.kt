package com.grepp.quizy.matching.api.game

import com.grepp.quizy.matching.api.sse.SseConnector
import com.grepp.quizy.matching.match.MatchingPoolManager
import com.grepp.quizy.matching.match.MatchingUseCase
import com.grepp.quizy.matching.user.UserId
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@RestController
@RequestMapping("/api/matching")
class GameMatchingApi(
    private val matchingUseCase: MatchingUseCase,
    private val sseConnector: SseConnector
) {

    @GetMapping(
        value = ["/subscribe"],
        produces = [MediaType.TEXT_EVENT_STREAM_VALUE]
    )
    fun subscribe(@RequestHeader("X-Auth-Id") userId: UserId): ResponseEntity<SseEmitter> {
        val emitter = sseConnector.connect(userId)
        matchingUseCase.registerWaiting(userId)
        return ResponseEntity.ok(emitter)
    }
}
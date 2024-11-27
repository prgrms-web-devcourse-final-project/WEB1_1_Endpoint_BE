package com.grepp.quizy.matching.api.game

import com.grepp.quizy.matching.api.sse.SseConnector
import com.grepp.quizy.matching.match.MatchingUseCase
import com.grepp.quizy.matching.user.UserId
import com.grepp.quizy.web.annotation.AuthUser
import com.grepp.quizy.web.dto.UserPrincipal
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
    fun subscribe(@AuthUser principal: UserPrincipal): ResponseEntity<SseEmitter> {
        val emitter = sseConnector.connect(UserId(principal.value))
        matchingUseCase.registerWaiting(UserId(principal.value))
        return ResponseEntity.ok(emitter)
    }
}
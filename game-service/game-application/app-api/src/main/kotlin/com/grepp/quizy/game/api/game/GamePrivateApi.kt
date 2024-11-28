package com.grepp.quizy.game.api.game

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.game.api.game.dto.*
import com.grepp.quizy.game.domain.game.GamePrivateService
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/game/private")
class GamePrivateApi(
    private val gamePrivateService: GamePrivateService
) {

    @PostMapping
    fun createGame(
        @RequestHeader("X-AUTH-ID") userId: String,
        @RequestBody request: GameCreateRequest
    ): ApiResponse<GameResponse> =
        ApiResponse.success(
            GameResponse.from(
                gamePrivateService.create(
                    userId.toLong(),
                    request.subject,
                    request.level,
                    request.quizCount
                )
            )
        )

    @PostMapping("/join")
    fun join(
        @RequestHeader("X-AUTH-ID") userId: String,
        @RequestParam code: String
    ): ApiResponse<GameResponse> =
        ApiResponse.success(
            GameResponse.from(
                gamePrivateService.join(
                    userId.toLong(),
                    code
                )
            )
        )

}
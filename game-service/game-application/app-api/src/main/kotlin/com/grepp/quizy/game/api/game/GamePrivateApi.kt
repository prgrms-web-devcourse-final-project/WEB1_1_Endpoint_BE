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

    @MessageMapping("/quit/{gameId}")
    fun quit(
        @DestinationVariable gameId: Long,
        principal: Principal
    ): ApiResponse<Unit> =
        ApiResponse.success(
            gamePrivateService.quit(
                principal.name.toLong(),
                gameId
            )
        )

    @MessageMapping("/update/{gameId}/subject")
    fun updateSubject(
        @DestinationVariable gameId: Long,
        @Payload request: UpdateSubjectPayloadRequest,
        principal: Principal
    ) {
        gamePrivateService.updateSubject(
            principal.name.toLong(),
            gameId,
            request.subject
        )
    }

    @MessageMapping("/update/{gameId}/level")
    fun updateLevel(
        @DestinationVariable gameId: Long,
        @Payload request: UpdateLevelPayloadRequest,
        principal: Principal
    ) {
        gamePrivateService.updateLevel(
            principal.name.toLong(),
            gameId,
            request.level
        )
    }

    @MessageMapping("/update/{gameId}/quiz-count")
    fun updateQuizCount(
        @DestinationVariable gameId: Long,
        @Payload request: UpdateQuizCountPayloadRequest,
        principal: Principal
    ) {
        gamePrivateService.updateQuizCount(
            principal.name.toLong(),
            gameId,
            request.quizCount
        )
    }

    @MessageMapping("/kick/{gameId}")
    fun kickUser(
        @DestinationVariable gameId: Long,
        @Payload request: KickUserPayloadRequest,
        principal: Principal
    ) {
        gamePrivateService.kickUser(
            principal.name.toLong(),
            gameId,
            request.targetUserId
        )
    }

}
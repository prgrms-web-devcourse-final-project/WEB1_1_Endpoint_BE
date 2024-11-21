package com.grepp.quizy.game.api.game

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.game.api.game.dto.*
import com.grepp.quizy.game.domain.GameService
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/game")
class GameApi(private val gameService: GameService) {

    @PostMapping
    fun createGame(
            @RequestHeader("X-AUTH-ID") userId: String,
            @RequestBody request: GameCreateRequest,
    ): ApiResponse<GameResponse> =
            ApiResponse.success(
                    GameResponse.from(
                            gameService.create(
                                    userId.toLong(),
                                    request.subject,
                                    request.level,
                                    request.quizCount,
                            )
                    )
            )

    @PostMapping("/join")
    fun join(
            @RequestHeader("X-AUTH-ID") userId: String,
            @RequestParam code: String,
    ): ApiResponse<GameResponse> =
            ApiResponse.success(
                    GameResponse.from(
                            gameService.join(userId.toLong(), code)
                    )
            )

    @PostMapping("/quit")
    fun quit(
            @RequestHeader("X-AUTH-ID") userId: String,
            @RequestParam code: String,
    ): ApiResponse<Unit> =
            ApiResponse.success(
                    gameService.quit(userId.toLong(), code)
            )

    @MessageMapping("/update/{gameId}/subject")
    fun updateSubject(
            @DestinationVariable gameId: Long,
            @Payload request: UpdateSubjectRequest,
            @Header("X-AUTH-ID") userId: String,
    ) {
        gameService.updateSubject(
                userId.toLong(),
                gameId,
                request.subject,
        )
    }

    @MessageMapping("/update/{gameId}/level")
    fun updateLevel(
            @DestinationVariable gameId: Long,
            @Payload request: UpdateLevelRequest,
            @Header("X-AUTH-ID") userId: String,
    ) {
        gameService.updateLevel(
                userId.toLong(),
                gameId,
                request.level,
        )
    }

    @MessageMapping("/update/{gameId}/quiz-count")
    fun updateQuizCount(
            @DestinationVariable gameId: Long,
            @Payload request: UpdateQuizCountRequest,
            @Header("X-AUTH-ID") userId: String,
    ) {
        gameService.updateQuizCount(
                userId.toLong(),
                gameId,
                request.quizCount,
        )
    }

    @MessageMapping("/kick/{gameId}")
    fun kickUser(
            @DestinationVariable gameId: Long,
            @Payload request: KickUserRequest,
            @Header("X-AUTH-ID") userId: String,
    ) {
        gameService.kickUser(
                userId.toLong(),
                gameId,
                request.targetUserId,
        )
    }
}

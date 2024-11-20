package com.grepp.quizy.game.api.game

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.game.api.game.dto.*
import com.grepp.quizy.game.domain.GameService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/game")
class GameApi(
    private val gameService: GameService
) {

    @PostMapping
    fun createGame(
        @RequestHeader("X-AUTH-ID") userId: String,
        @RequestBody request: GameCreateRequest
    ): ApiResponse<GameResponse> =
        ApiResponse.success(
            GameResponse.from(
                gameService.create(
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
                gameService.join(
                    userId.toLong(),
                    code
                )
            )
        )

    @PostMapping("/quit")
    fun quit(
        @RequestHeader("X-AUTH-ID") userId: String,
        @RequestParam code: String
    ): ApiResponse<Unit> =
        ApiResponse.success(
            gameService.quit(
                userId.toLong(),
                code
            )
        )

    @MessageMapping("/update/subject")
    fun updateSubject(
        @RequestBody request: UpdateSubjectRequest,
        @RequestHeader("X-AUTH-ID") userId: String
    ) {
        gameService.updateSubject(
            userId.toLong(),
            request.gameId,
            request.subject
        )
    }

    @MessageMapping("/update/level")
    fun updateLevel(
        @RequestBody request: UpdateLevelRequest,
        @RequestHeader("X-AUTH-ID") userId: String
    ) {
        gameService.updateLevel(
            userId.toLong(),
            request.gameId,
            request.level
        )
    }

    @MessageMapping("/update/quiz-count")
    fun updateQuizCount(
        @RequestBody request: UpdateQuizCountRequest,
        @RequestHeader("X-AUTH-ID") userId: String
    ) {
        gameService.updateQuizCount(
            userId.toLong(),
            request.gameId,
            request.quizCount
        )
    }

}
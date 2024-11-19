package com.grepp.quizy.game.api.game

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.game.api.game.dto.GameCreateRequest
import com.grepp.quizy.game.api.game.dto.GameResponse
import com.grepp.quizy.game.domain.GameService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/game")
class GameApi(
    private val gameService: GameService
) {

    @PostMapping
    fun createGame(
        @RequestHeader("X-USER-ID") userId: String,
        @RequestBody request: GameCreateRequest
    ): ApiResponse<GameResponse> {
        return ApiResponse.success(
            GameResponse.from(
                gameService.create(
                    userId.toLong(),
                    request.subject,
                    request.level,
                    request.quizCount
                )
            )
        )
    }

    @PostMapping("/join/{gameId}")
    fun join(
        @RequestHeader("X-USER-ID") userId: String,
        @PathVariable gameId: Long
    ): ApiResponse<GameResponse> {
        return ApiResponse.success(GameResponse.from(gameService.join(gameId, userId.toLong())))
    }

    @PostMapping("/quit/{gameId}")
    fun quit(
        @RequestHeader("X-USER-ID") userId: String,
        @PathVariable gameId: Long
    ): ApiResponse<Unit> {
        return ApiResponse.success(gameService.quit(gameId, userId.toLong()))
    }

}
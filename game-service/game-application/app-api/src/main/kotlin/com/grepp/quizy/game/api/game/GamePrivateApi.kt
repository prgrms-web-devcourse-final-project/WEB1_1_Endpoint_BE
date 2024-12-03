package com.grepp.quizy.game.api.game

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.game.api.game.dto.GameCreateRequest
import com.grepp.quizy.game.api.game.dto.GameResponse
import com.grepp.quizy.game.domain.game.GameLevel
import com.grepp.quizy.game.domain.game.GamePrivateService
import com.grepp.quizy.game.domain.game.GameSubject
import com.grepp.quizy.web.annotation.AuthUser
import com.grepp.quizy.web.dto.UserPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/game/private")
class GamePrivateApi(
    private val gamePrivateService: GamePrivateService
) {

    @PostMapping
    fun createGame(
        @AuthUser userPrincipal: UserPrincipal,
        @RequestBody request: GameCreateRequest
    ): ApiResponse<GameResponse> =
        ApiResponse.success(
            GameResponse.from(
                gamePrivateService.create(
                    userPrincipal.value,
                    request.subject,
                    request.level,
                    request.quizCount
                )
            )
        )

    @PostMapping("/join")
    fun join(
        @AuthUser userPrincipal: UserPrincipal,
        @RequestParam code: String
    ): ApiResponse<GameResponse> =
        ApiResponse.success(
            GameResponse.from(
                gamePrivateService.join(
                    userPrincipal.value,
                    code
                )
            )
        )

    @PostMapping("/start")
    fun start(
        @AuthUser userPrincipal: UserPrincipal,
        @RequestParam gameId: Long
    ): Unit =
        gamePrivateService.start(
            userPrincipal.value,
            gameId
        )
}
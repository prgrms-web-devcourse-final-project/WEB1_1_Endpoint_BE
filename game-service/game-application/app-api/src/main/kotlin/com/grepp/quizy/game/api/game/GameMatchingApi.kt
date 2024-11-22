package com.grepp.quizy.game.api.game

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.game.api.game.dto.GameResponse
import com.grepp.quizy.game.api.game.dto.MatchingRequest
import com.grepp.quizy.game.domain.GameMatchingService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/game/matching")
class GameMatchingApi(
    private val gameMatchingService: GameMatchingService,
) {

    // 매칭 서비스에서 요청을 받아 게임을 생성하고, 생성된 게임을 반환한다.
    @PostMapping
    fun createRandomGame(
        @RequestBody request: MatchingRequest
    ) = ApiResponse.success(
        GameResponse.from(
            gameMatchingService.create(
                userIds = request.userIds,
                subject = request.subject
            )
        )
    )

}
package com.grepp.quizy.game.controller.game

import com.grepp.quizy.game.domain.game.GameMatchingService
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import java.security.Principal

@Controller
class GameMatchingController(
    private val gameMatchingService: GameMatchingService
) {

    @MessageMapping("/join/{gameId}")
    fun joinRandomGame(
        @DestinationVariable gameId: Long,
        principal: Principal
    ) {
        gameMatchingService.join(
            principal.name.toLong(),
            gameId
        )
    }

}
package com.grepp.quizy.game.controller.game

import com.grepp.quizy.game.api.game.dto.SubmitAnswerRequest
import com.grepp.quizy.game.domain.game.GamePlayService
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Controller
import java.security.Principal

@Controller
class GamePlayingController(
    private val gamePlayService: GamePlayService
) {

    @MessageMapping("/submit/{gameId}")
    fun submit(
        @DestinationVariable gameId: Long,
        principal: Principal,
        @Payload request: SubmitAnswerRequest
    ) {
        gamePlayService.submitChoice(
            gameId,
            principal.name.toLong(),
            request.quizId,
            request.answer,
        )
    }

}
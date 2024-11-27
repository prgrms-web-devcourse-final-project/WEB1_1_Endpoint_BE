package com.grepp.quizy.game.api.game

import com.grepp.quizy.game.api.game.dto.SubmitAnswerRequest
import com.grepp.quizy.game.domain.game.GamePlayService
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class GamePlayingApi(
    private val gamePlayService: GamePlayService
) {

    @MessageMapping("/submit/{gameId}")
    fun submit(
        @DestinationVariable gameId: Long,
        principal: Principal,
        @Payload request: SubmitAnswerRequest
    ) {
        gamePlayService.submitAnswer(
            gameId,
            principal.name.toLong(),
            request.quizId,
            request.answer,
            request.submissionTimestamp
        )
    }

}
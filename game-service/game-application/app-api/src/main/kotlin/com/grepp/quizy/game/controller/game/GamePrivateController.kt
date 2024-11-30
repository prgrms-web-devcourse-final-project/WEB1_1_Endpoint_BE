package com.grepp.quizy.game.controller.game

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.game.api.game.dto.KickUserPayloadRequest
import com.grepp.quizy.game.api.game.dto.UpdateLevelPayloadRequest
import com.grepp.quizy.game.api.game.dto.UpdateQuizCountPayloadRequest
import com.grepp.quizy.game.api.game.dto.UpdateSubjectPayloadRequest
import com.grepp.quizy.game.domain.game.GameLevel
import com.grepp.quizy.game.domain.game.GamePrivateService
import com.grepp.quizy.game.domain.game.GameSubject
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Controller
import java.security.Principal

@Controller
class GamePrivateController(
    private val gamePrivateService: GamePrivateService
) {

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
            GameSubject.fromString(request.subject)
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
            GameLevel.fromString(request.level)
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

    @MessageMapping("/start/{gameId}")
    fun start(
        @DestinationVariable gameId: Long,
        principal: Principal
    ) {
        gamePrivateService.start(
            principal.name.toLong(),
            gameId
        )
    }

}
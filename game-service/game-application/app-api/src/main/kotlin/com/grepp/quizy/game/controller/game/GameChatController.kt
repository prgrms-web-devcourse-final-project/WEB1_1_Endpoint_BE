package com.grepp.quizy.game.controller.game

import com.grepp.quizy.game.api.game.dto.ChatPayloadRequest
import com.grepp.quizy.game.domain.game.GameChatService
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Controller
import java.security.Principal

@Controller
class GameChatController(
    private val gameChatService: GameChatService
) {

    @MessageMapping("/{gameId}")
    fun chat(
        @DestinationVariable gameId: Long,
        @Payload request: ChatPayloadRequest,
        principal: Principal
    ) {
        gameChatService.publishChat(
            principal.name.toLong(),
            gameId,
            request.message
        )
    }

}
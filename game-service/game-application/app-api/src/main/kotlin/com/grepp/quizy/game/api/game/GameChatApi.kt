package com.grepp.quizy.game.api.game

import com.grepp.quizy.game.api.game.dto.ChatPayloadRequest
import com.grepp.quizy.game.domain.game.GameChatService
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/game/chat")
class GameChatApi(
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
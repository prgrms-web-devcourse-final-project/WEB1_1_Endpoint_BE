package com.grepp.quizy.game.api.global.exception

import com.grepp.quizy.common.exception.CustomException
import com.grepp.quizy.game.domain.ErrorPayload
import com.grepp.quizy.game.domain.GameMessage
import com.grepp.quizy.game.domain.exception.GameException
import com.grepp.quizy.game.domain.game.GameMessageSender
import org.springframework.messaging.handler.annotation.MessageExceptionHandler
import org.springframework.web.bind.annotation.ControllerAdvice
import java.security.Principal

@ControllerAdvice
class GlobalWebSocketExceptionHandler(
    private val gameMessageSender: GameMessageSender
) {

    @MessageExceptionHandler(CustomException::class)
    fun handleGameException(
        principal: Principal,
        exception: GameException
    ) {
        gameMessageSender.send(
            principal.name,
            GameMessage.error(
                payload = ErrorPayload.of(
                    exception.errorCode.errorReason.errorCode,
                    exception.message
                )
            )
        )
    }

    @MessageExceptionHandler(Exception::class)
    fun handleException(
        principal: Principal,
        exception: Exception
    ) {
        gameMessageSender.send(
            principal.name,
            GameMessage.error(
                payload = ErrorPayload.of(
                    message = exception.message ?: "Internal Server Error"
                )
            )
        )
    }

}
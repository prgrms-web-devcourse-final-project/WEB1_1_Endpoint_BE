package com.grepp.quizy.game.api.exception

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.game.domain.exception.GameException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(GameException::class)
    protected fun handleGameException(e: GameException, request: HttpServletRequest): ApiResponse<Unit> {
        return ApiResponse.error(
            e.errorCode.errorReason,
            request.requestURI,
            e.message
        )
    }

}
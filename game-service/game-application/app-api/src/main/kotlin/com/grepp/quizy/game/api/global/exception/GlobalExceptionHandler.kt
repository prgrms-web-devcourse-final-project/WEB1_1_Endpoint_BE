package com.grepp.quizy.game.api.global.exception

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.common.exception.CustomException
import com.grepp.quizy.common.exception.ErrorReason
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(CustomException::class)
    protected fun handleCustomException(
        e: CustomException,
        request: HttpServletRequest,
    ): ApiResponse<Unit> {
        return ApiResponse.error(
            e.errorCode.errorReason,
            request.requestURI,
            e.message,
        )
    }

    @ExceptionHandler(Exception::class)
    protected fun handleException(
        e: Exception,
        request: HttpServletRequest,
    ): ApiResponse<Unit> {
        return ApiResponse.error(
            ErrorReason.of(
                500,
                "INTERNAL_SERVER_ERROR",
                "Internal Server Error",
            ),
            request.requestURI,
            e.message ?: "Internal Server Error",
        )
    }
}

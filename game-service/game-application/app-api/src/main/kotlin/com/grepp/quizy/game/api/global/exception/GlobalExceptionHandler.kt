package com.grepp.quizy.game.api.global.exception

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.common.exception.CustomException
import com.grepp.quizy.common.exception.ErrorReason
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

private val log = KotlinLogging.logger {}

@RestControllerAdvice
class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
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

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    protected fun handleException(
        e: Exception,
        request: HttpServletRequest,
    ): ApiResponse<Unit> {
        log.error(
            """
            [Exception] URI: ${request.requestURI}
            Exception Type: ${e.javaClass.simpleName}
            Message: ${e.message}
            StackTrace:
            ${e.stackTraceToString()}
            """.trimIndent()
        )

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

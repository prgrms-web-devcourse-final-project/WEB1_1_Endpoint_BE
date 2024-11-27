package com.grepp.quizy.quiz.api.global.exception

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.common.exception.CustomException
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MissingRequestHeaderException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

private val log = KotlinLogging.logger {}

@RestControllerAdvice
class QuizControllerAdvice {
    companion object {
        private const val UNKNOWN_ERROR_CODE = "INTERNAL_SERVER_ERROR"
        private const val UNKNOWN_ERROR_MESSAGE = "알 수 없는 서버 에러입니다"
        private const val UNAUTHORIZED_ERROR_CODE = "UNAUTHORIZED"
        private const val UNAUTHORIZED_ERROR_MESSAGE = "인증 정보가 필요합니다"
    }

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(
            exception: CustomException,
            request: HttpServletRequest,
    ): ResponseEntity<ApiResponse<Unit>> =
            ResponseEntity.status(exception.status)
                    .body(
                            ApiResponse.error(
                                    exception.errorCode.errorReason,
                                    request.requestURI,
                                    exception.message,
                            )
                    )

    @ExceptionHandler(MissingRequestHeaderException::class)
    fun handleMissingRequestHeaderException(
        exception: MissingRequestHeaderException,
        request: HttpServletRequest,
    ): ResponseEntity<ApiResponse<Unit>> =
        ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(
                ApiResponse.error(
                    UNAUTHORIZED_ERROR_CODE,
                    UNAUTHORIZED_ERROR_MESSAGE
                )
            )

    @ExceptionHandler(Exception::class)
    fun handleException(
            exception: Exception,
            request: HttpServletRequest,
    ): ResponseEntity<ApiResponse<Unit>> {
        log.error(exception) { "Unexpected error occurred" }
        return ResponseEntity.internalServerError()
                .body(
                        ApiResponse.error(
                                UNKNOWN_ERROR_CODE,
                                exception.message
                                        ?: UNKNOWN_ERROR_MESSAGE,
                        )
                )
    }
}

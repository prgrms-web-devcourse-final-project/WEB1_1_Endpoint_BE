package com.grepp.quizy.common.api

import com.fasterxml.jackson.annotation.JsonInclude
import com.grepp.quizy.common.exception.ErrorReason
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ApiResponse<T>(
        val path: String? = null,
        val responseCode: String,
        val message: String? = null,
        val result: T? = null,
        val timeStamp: LocalDateTime = LocalDateTime.now(),
) {
    companion object {
        fun <T> success(result: T): ApiResponse<T> =
                ApiResponse(responseCode = "SUCCESS", result = result)

        fun success(): ApiResponse<Unit> = ApiResponse(responseCode = "SUCCESS")

        fun success(message: String): ApiResponse<Unit> =
                ApiResponse(responseCode = "SUCCESS", message = message)

        fun error(
                errorReason: ErrorReason,
                path: String,
                message: String,
        ): ApiResponse<Unit> =
                ApiResponse(
                        path = path,
                        responseCode = errorReason.errorCode,
                        message = message,
                )

        fun error(errorCode: String, message: String): ApiResponse<Unit> =
                ApiResponse(responseCode = errorCode, message = message)
    }
}

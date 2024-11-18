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
        @JvmStatic
        fun <T> success(result: T): ApiResponse<T> =
                ApiResponse(responseCode = "SUCCESS", result = result)

        @JvmStatic
        fun success(): ApiResponse<Unit> = ApiResponse(responseCode = "SUCCESS")

        @JvmStatic
        fun success(message: String): ApiResponse<Unit> =
                ApiResponse(responseCode = "SUCCESS", message = message)

        @JvmStatic
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

        @JvmStatic
        fun error(errorCode: String, message: String): ApiResponse<Unit> =
                ApiResponse(responseCode = errorCode, message = message)
    }
}

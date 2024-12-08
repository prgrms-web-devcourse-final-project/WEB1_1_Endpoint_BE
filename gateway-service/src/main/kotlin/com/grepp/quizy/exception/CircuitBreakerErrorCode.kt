package com.grepp.quizy.exception

import com.grepp.quizy.common.exception.BaseErrorCode
import com.grepp.quizy.common.exception.ErrorReason
import org.springframework.http.HttpStatus

enum class CircuitBreakerErrorCode(
    private val status: Int,
    private val errorCode: String,
    private val message: String,
) : BaseErrorCode {

    GAME_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE.value(), "CIRCUIT_BREAKER_503", "게임 서비스를 이용할 수 없습니다."),
    MATCHING_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE.value(), "CIRCUIT_BREAKER_503", "매칭 서비스를 이용할 수 없습니다."),
    QUIZ_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE.value(), "CIRCUIT_BREAKER_503", "퀴즈 서비스를 이용할 수 없습니다."),
    USER_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE.value(), "CIRCUIT_BREAKER_503", "사용자 서비스를 이용할 수 없습니다."),
    WS_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE.value(), "CIRCUIT_BREAKER_503", "웹소켓 서비스를 이용할 수 없습니다."),
    ;

    override val errorReason: ErrorReason
        get() = ErrorReason(status, errorCode, message)
}
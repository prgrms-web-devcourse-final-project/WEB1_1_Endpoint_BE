package com.grepp.quizy.user.api.global.jwt.exception

import com.grepp.quizy.common.constants.QuizyConstants.BAD_REQUEST
import com.grepp.quizy.common.constants.QuizyConstants.INTERNAL_SERVER
import com.grepp.quizy.common.constants.QuizyConstants.NOT_FOUND
import com.grepp.quizy.common.constants.QuizyConstants.UNAUTHORIZED
import com.grepp.quizy.common.exception.BaseErrorCode
import com.grepp.quizy.common.exception.ErrorReason

enum class JwtErrorCode(
    private val status: Int,
    private val errorCode: String,
    private val message: String,
) : BaseErrorCode {

    UNSUPPORTED_TOKEN(BAD_REQUEST, "TOKEN_400", "지원 하지 않은 토큰"),
    TOKEN_EXPIRED(UNAUTHORIZED, "TOKEN_401", "만료된 토큰"),
    TOKEN_NOT_VALIDATE(UNAUTHORIZED, "TOKEN_401", "유효하지 않은 토큰"),
    TOKEN_BAD_SIGNATURE(UNAUTHORIZED, "TOKEN_401", "서명 불일치"),
    ACCESS_TOKEN_NOT_FOUNT(NOT_FOUND, "TOKEN_404", "ACCESS TOKEN이 없음"),
    UNKNOWN_EXCEPTION(INTERNAL_SERVER, "TOKEN_900", "알 수 없는 오류 발생");

    override val errorReason: ErrorReason
        get() = ErrorReason(status, errorCode, message)
}
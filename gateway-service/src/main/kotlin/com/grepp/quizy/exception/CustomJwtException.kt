package com.grepp.quizy.exception

import com.grepp.quizy.common.exception.WebException
import com.grepp.quizy.user.api.global.jwt.exception.JwtErrorCode
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.UnsupportedJwtException

sealed class CustomJwtException(errorCode: JwtErrorCode) : WebException(errorCode) {

    data object JwtExpriedException : CustomJwtException(JwtErrorCode.TOKEN_EXPIRED) {
        private fun readResolve(): Any = JwtExpriedException
        val EXCEPTION: CustomJwtException = JwtExpriedException
    }

    data object JwtNotFountException : CustomJwtException(JwtErrorCode.TOKEN_NOT_FOUNT) {
        private fun readResolve(): Any = JwtNotFountException
        val EXCEPTION: CustomJwtException = JwtNotFountException
    }

    data object JwtNotValidateException : JwtException(JwtErrorCode.TOKEN_NOT_VALIDATE.toString()) {
        private fun readResolve(): Any = JwtNotValidateException
        val EXCEPTION: JwtException = JwtException("토큰이 유효하지 않습니다.")
    }

    data object JwtUnknownException : CustomJwtException(JwtErrorCode.UNKNOWN_EXCEPTION) {
        private fun readResolve(): Any = JwtUnknownException
        val EXCEPTION: CustomJwtException = JwtUnknownException
    }

    data object JwtUnsupportedException : JwtException(JwtErrorCode.UNSUPPORTED_TOKEN.toString()) {
        private fun readResolve(): Any = JwtUnsupportedException
        val EXCEPTION: JwtException = UnsupportedJwtException("지원하지 않는 토큰입니다.")
    }

    data object JwtLoggedOutException : CustomJwtException(JwtErrorCode.LOGGED_OUT_USER) {
        private fun readResolve(): Any = JwtLoggedOutException
        val EXCEPTION: CustomJwtException = JwtLoggedOutException
    }
}
package com.grepp.quizy.infra.jwt.exception

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.UnsupportedJwtException

object JwtUnsupportedException : JwtException(JwtErrorCode.UNSUPPORTED_TOKEN.toString()) {
    val EXCEPTION: JwtException = UnsupportedJwtException("지원하지 않는 토큰입니다.")
}
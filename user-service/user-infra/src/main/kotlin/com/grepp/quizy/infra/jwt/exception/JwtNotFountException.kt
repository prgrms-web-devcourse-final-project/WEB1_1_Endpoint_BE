package com.grepp.quizy.infra.jwt.exception

import com.grepp.quizy.common.exception.WebException

object JwtNotFountException : WebException(JwtErrorCode.ACCESS_TOKEN_NOT_FOUNT) {
    val EXCEPTION: WebException = JwtNotFountException
}
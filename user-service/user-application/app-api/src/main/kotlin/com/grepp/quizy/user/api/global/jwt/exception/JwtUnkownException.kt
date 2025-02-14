package com.grepp.quizy.user.api.global.jwt.exception

import com.grepp.quizy.common.exception.WebException

object JwtUnkownException : WebException(JwtErrorCode.UNKNOWN_EXCEPTION) {
    val EXCEPTION: WebException = JwtUnkownException
}
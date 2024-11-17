package com.grepp.quizy.infra.jwt.exception

import com.grepp.quizy.common.exception.WebException

object JwtExpriedException : WebException(JwtErrorCode.TOKEN_EXPIRED) {
    val EXCEPTION: WebException = JwtExpriedException
}
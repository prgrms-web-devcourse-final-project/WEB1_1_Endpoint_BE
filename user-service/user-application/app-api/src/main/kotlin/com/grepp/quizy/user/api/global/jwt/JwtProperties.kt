package com.grepp.quizy.user.api.global.jwt

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class JwtProperties{
    @Value("\${jwt.secret}")
    lateinit var secret: String

    @Value("\${jwt.access-token-validity}")
    var accessTokenValidity: Long = 0

    @Value("\${jwt.refresh-token-validity}")
    var refreshTokenValidity: Long = 0
}
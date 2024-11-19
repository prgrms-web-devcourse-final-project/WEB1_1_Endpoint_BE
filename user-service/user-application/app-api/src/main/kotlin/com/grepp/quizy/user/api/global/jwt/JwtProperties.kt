package com.grepp.quizy.user.api.global.jwt

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class JwtProperties(
    @Value("\${jwt.secret}")
    val secret: String,
    @Value("\${jwt.access-token-validity}")
    val accessTokenValidity: Long,
    @Value("\${jwt.refresh-token-validity}")
    val refreshTokenValidity: Long
)
package com.grepp.quizy.user.api.global.jwt.dto

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val refreshTokenExpirationTime: Long,
    val isGuest: Boolean
)
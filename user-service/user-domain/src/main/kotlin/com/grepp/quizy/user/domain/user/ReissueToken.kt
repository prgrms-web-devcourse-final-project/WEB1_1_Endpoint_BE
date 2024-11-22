package com.grepp.quizy.user.domain.user

data class ReissueToken(
    val accessToken: String,
    val refreshToken: String,
    val refreshTokenExpirationTime: Long,
    val isGuest: Boolean
) {
}
package com.grepp.quizy.user.api.global.jwt.dto

import com.grepp.quizy.user.domain.user.ReissueToken

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val refreshTokenExpirationTime: Long,
    val guest: Boolean
) {
    companion object {
        fun from(reissueToken: ReissueToken): TokenResponse {
            return TokenResponse(
                reissueToken.accessToken,
                reissueToken.refreshToken,
                reissueToken.refreshTokenExpirationTime,
                reissueToken.isGuest
            )
        }
    }
}
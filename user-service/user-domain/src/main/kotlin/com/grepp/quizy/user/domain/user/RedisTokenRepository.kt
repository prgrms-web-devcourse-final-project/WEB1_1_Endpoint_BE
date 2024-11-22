package com.grepp.quizy.user.domain.user

interface RedisTokenRepository {
    fun saveRefreshToken(userId: UserId, refreshToken: String, expirationTime: Long)
    fun deleteRefreshToken(userId: UserId)
    fun saveLogoutToken(accessToken: String)
}
package com.grepp.quizy.user.domain.user

interface RedisTokenRepository {
    fun saveRefreshToken(userId: UserId, refreshToken: String, expirationTime: Long)
    fun saveSession(userId: UserId)
    fun removeSession(userId: UserId)
    fun hasLoggedInUser(userId: UserId): Boolean
    fun deleteRefreshToken(userId: UserId)
    fun saveLogoutToken(accessToken: String)
}
package com.grepp.quizy.user.domain.user

interface RedisTokenRepository {
    fun saveRefreshToken(userId: UserId, refreshToken: String, expirationTime: Long)
    fun removeSession(userId: UserId)
    fun hasLoggedInUser(userId: UserId): Boolean
    fun deleteRefreshToken(userId: UserId)
    fun saveSession(userId: UserId, expirationTime: Long)
    fun saveUser(userId: UserId)
    fun removeUser(userId: UserId)
    fun isExistUser(userId: UserId): Boolean
    fun saveLogoutToken(userId: UserId, accessToken: String)
}
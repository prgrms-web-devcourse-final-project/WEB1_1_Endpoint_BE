package com.grepp.quizy.user

import com.grepp.quizy.global.RedisUtil
import org.springframework.stereotype.Repository

@Repository
class RedisTokenRepository(private val redisUtil: RedisUtil) {
    companion object {
        private const val REFRESH_TOKEN_KEY_PREFIX = "refresh_token:"
        private const val LOGOUT_TOKEN_KEY = "logout"
        private const val USER_KEY = "user"
    }


    fun saveRefreshToken(
        userId: UserId,
        refreshToken: String,
        expirationTime: Long,
    ) {
        val key = generateRefreshTokenKey(userId)
        redisUtil.saveValue(key, refreshToken, expirationTime)
    }

    fun getRefreshToken(userId: UserId): String? {
        val key = generateRefreshTokenKey(userId)
        return redisUtil.getValue(key)
    }

    fun deleteRefreshToken(userId: UserId) {
        val key = generateRefreshTokenKey(userId)
        redisUtil.deleteValue(key)
    }

    fun saveLogoutToken(accessToken: String) {
        redisUtil.saveSet(LOGOUT_TOKEN_KEY, accessToken)
    }

    fun isAlreadyLogin(accessToken: String): Boolean {
        return !redisUtil.isExistSet(LOGOUT_TOKEN_KEY, accessToken)
    }

    fun isExistUser(userId: UserId): Boolean {
        return redisUtil.isExistSet(USER_KEY, userId.value.toString())
    }

    private fun generateRefreshTokenKey(userId: UserId): String =
        "$REFRESH_TOKEN_KEY_PREFIX${userId.value}"
}

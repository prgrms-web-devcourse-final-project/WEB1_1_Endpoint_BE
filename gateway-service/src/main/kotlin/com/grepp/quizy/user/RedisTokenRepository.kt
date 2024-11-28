package com.grepp.quizy.user

import com.grepp.quizy.global.RedisUtil
import org.springframework.stereotype.Repository

@Repository
class RedisTokenRepository(private val redisUtil: RedisUtil) {
    companion object {
        private const val REFRESH_TOKEN_KEY_PREFIX = "refresh_token:"
        private const val LOGOUT_TOKEN_KEY_PREFIX = "logout:"
        private const val USER_KEY_PREFIX = "user:"
    }


    fun saveRefreshToken(
        userId: UserId,
        refreshToken: String,
        expirationTime: Long,
    ) {
        redisUtil.saveValue(generateRefreshTokenKey(userId), refreshToken, expirationTime)
    }

    fun getRefreshToken(userId: UserId): String? {
        return redisUtil.getValue(generateRefreshTokenKey(userId))
    }

    fun deleteRefreshToken(userId: UserId) {
        redisUtil.deleteValue(generateRefreshTokenKey(userId))
    }

    fun saveLogoutToken(userId: UserId, accessToken: String) {
        redisUtil.saveSet(generateLogoutTokenKey(userId), accessToken)
    }

    fun isAlreadyLogin(userId: UserId, accessToken: String): Boolean {
        return !redisUtil.isExistSet(generateLogoutTokenKey(userId), accessToken)
    }

    fun isExistUser(userId: UserId): Boolean {
        return redisUtil.isExistSet(generateUserKey(userId), userId.value.toString())
    }

    private fun generateRefreshTokenKey(userId: UserId): String = "$REFRESH_TOKEN_KEY_PREFIX${userId.value}"
    private fun generateLogoutTokenKey(userId: UserId): String = "$LOGOUT_TOKEN_KEY_PREFIX${userId.value}"
    private fun generateUserKey(userId: UserId): String = "$USER_KEY_PREFIX${userId.value}"
}

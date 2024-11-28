package com.grepp.quizy.user.infra.redis.repository

import com.grepp.quizy.user.domain.user.RedisTokenRepository
import com.grepp.quizy.user.domain.user.UserId
import com.grepp.quizy.user.infra.redis.util.RedisUtil
import org.springframework.stereotype.Repository

@Repository
class RedisTokenRepositoryAdaptor(
    private val redisUtil: RedisUtil
) : RedisTokenRepository {
    companion object {
        private const val REFRESH_TOKEN_KEY_PREFIX = "refresh_token:"
        private const val LOGOUT_TOKEN_KEY_PREFIX = "logout:"
        private const val USER_SESSION_KEY_PREFIX = "session:"
        private const val USER_KEY_PREFIX = "user:"
    }


    override fun saveRefreshToken(userId: UserId, refreshToken: String, expirationTime: Long) {
        redisUtil.saveValue(generateRefreshTokenKey(userId), refreshToken, expirationTime)
    }

    fun getRefreshToken(userId: UserId): String? {
        return redisUtil.getValue(generateRefreshTokenKey(userId))
    }

    override fun deleteRefreshToken(userId: UserId) {
        redisUtil.deleteValue(generateRefreshTokenKey(userId))
    }

    override fun saveLogoutToken(userId: UserId, accessToken: String) {
        redisUtil.saveSet(generateLogoutTokenKey(userId), accessToken)
    }

    fun isAlreadyLogin(userId: UserId, accessToken: String): Boolean {
        return !redisUtil.isExistSet(generateLogoutTokenKey(userId), accessToken)
    }

    override fun saveSession(userId: UserId, expirationTime: Long) {
        redisUtil.saveSet(generateUserSessionKey(userId), userId.value.toString(), expirationTime)
    }

    override fun hasLoggedInUser(userId: UserId): Boolean {
        return redisUtil.isExistSet(generateUserSessionKey(userId), userId.value.toString())
    }

    override fun removeSession(userId: UserId) {
        redisUtil.deleteSet(generateUserSessionKey(userId), userId.value.toString())
    }

    override fun saveUser(userId: UserId) {
        redisUtil.saveSet(generateUserKey(userId), userId.value.toString())
    }

    override fun isExistUser(userId: UserId): Boolean {
        return redisUtil.isExistSet(generateUserKey(userId), userId.value.toString())
    }

    override fun removeUser(userId: UserId) {
        redisUtil.deleteSet(generateUserKey(userId), userId.value.toString())
    }

    private fun generateRefreshTokenKey(userId: UserId): String = "$REFRESH_TOKEN_KEY_PREFIX${userId.value}"
    private fun generateLogoutTokenKey(userId: UserId): String = "$LOGOUT_TOKEN_KEY_PREFIX${userId.value}"
    private fun generateUserSessionKey(userId: UserId): String = "$USER_SESSION_KEY_PREFIX${userId.value}"
    private fun generateUserKey(userId: UserId): String = "$USER_KEY_PREFIX${userId.value}"

}
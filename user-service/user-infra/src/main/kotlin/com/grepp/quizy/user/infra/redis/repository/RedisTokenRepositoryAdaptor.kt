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
        private const val LOGOUT_TOKEN_KEY = "logout"
        private const val USER_SESSION_KEY = "session"
        private const val USER_KEY = "user"
    }


    override fun saveRefreshToken(userId: UserId, refreshToken: String, expirationTime: Long) {
        val key = generateRefreshTokenKey(userId)
        redisUtil.saveValue(key, refreshToken, expirationTime)
    }

    fun getRefreshToken(userId: UserId): String? {
        val key = generateRefreshTokenKey(userId)
        return redisUtil.getValue(key)
    }

    override fun deleteRefreshToken(userId: UserId) {
        val key = generateRefreshTokenKey(userId)
        redisUtil.deleteValue(key)
    }

    override fun saveLogoutToken(accessToken: String) {
        redisUtil.saveSet(LOGOUT_TOKEN_KEY, accessToken)
    }

    fun isAlreadyLogin(accessToken: String): Boolean {
        return !redisUtil.isExistSet(LOGOUT_TOKEN_KEY, accessToken)
    }

    override fun saveSession(userId: UserId, expirationTime: Long) {
        redisUtil.saveSet(USER_SESSION_KEY, userId.value.toString(), expirationTime)
    }

    override fun hasLoggedInUser(userId: UserId): Boolean {
        return redisUtil.isExistSet(USER_SESSION_KEY, userId.value.toString())
    }

    override fun removeSession(userId: UserId) {
        redisUtil.deleteSet(USER_SESSION_KEY, userId.value.toString())
    }

    override fun saveUser(userId: UserId) {
        redisUtil.saveSet(USER_KEY, userId.value.toString())
    }

    override fun isExistUser(userId: UserId): Boolean {
        return redisUtil.isExistSet(USER_KEY, userId.value.toString())
    }

    override fun removeUser(userId: UserId) {
        redisUtil.deleteSet(USER_KEY, userId.value.toString())
    }

    private fun generateRefreshTokenKey(userId: UserId): String = "$REFRESH_TOKEN_KEY_PREFIX${userId.value}"
}
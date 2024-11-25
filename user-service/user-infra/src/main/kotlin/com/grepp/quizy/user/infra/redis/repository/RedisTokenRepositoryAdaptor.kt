package com.grepp.quizy.user.infra.redis.repository

import com.grepp.quizy.user.domain.user.RedisTokenRepository
import com.grepp.quizy.user.domain.user.UserId
import com.grepp.quizy.user.infra.redis.util.RedisUtil
import org.springframework.stereotype.Repository

@Repository
class RedisTokenRepositoryAdaptor(
    private val redisUtil: RedisUtil
) : RedisTokenRepository {
    private val REFRESH_TOKEN_KEY_PREFIX = "refresh_token:"
    private val LOGOUT_TOKEN_KEY = "logout"


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

    private fun generateRefreshTokenKey(userId: UserId): String = "$REFRESH_TOKEN_KEY_PREFIX${userId.value}"
}
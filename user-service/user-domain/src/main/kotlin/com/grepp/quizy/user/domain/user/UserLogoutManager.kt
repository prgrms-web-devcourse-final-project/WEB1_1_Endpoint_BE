package com.grepp.quizy.user.domain.user

import org.springframework.stereotype.Component

@Component
class UserLogoutManager(
    private val redisRepository: RedisTokenRepository
) {
    fun logout(userId: UserId, accessToken: String) {
        redisRepository.saveLogoutToken(userId, accessToken)
        redisRepository.deleteRefreshToken(userId)
        redisRepository.removeSession(userId)
    }

}
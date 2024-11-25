package com.grepp.quizy.user.domain.user

import com.grepp.quizy.user.domain.user.exception.CustomUserException
import org.springframework.stereotype.Component

@Component
class UserLoginManager(
    private val redisTokenRepository: RedisTokenRepository
) {
    fun login(userId: UserId) {
        // 이미 로그인 한 사용자가 있는 경우 예외 발생
        if (redisTokenRepository.hasLoggedInUser(userId)) {
            throw CustomUserException.DuplicateLoginException
        }
        redisTokenRepository.saveSession(userId)
    }
}
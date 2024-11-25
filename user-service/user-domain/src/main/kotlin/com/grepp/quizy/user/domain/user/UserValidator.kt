package com.grepp.quizy.user.domain.user

import org.springframework.stereotype.Component

@Component
class UserValidator(
    private val userRepository: UserRepository,
    private val redisTokenRepository: RedisTokenRepository
) {
    // 유저가 실제로 존재하는지 확인
    fun isValid(userId: UserId): Boolean {
        if (!redisTokenRepository.isExistUser(userId)) {
            if (!userRepository.existsById(userId.value)) {
                return false
            }
            redisTokenRepository.saveUser(userId)
        }
        return true
    }


}
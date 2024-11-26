package com.grepp.quizy.user.domain.user

import com.grepp.quizy.user.domain.user.exception.CustomUserException
import org.springframework.stereotype.Component

@Component
class UserRemover(
    private val userRepository: UserRepository,
    private val redisTokenRepository: RedisTokenRepository,
    private val userMessageSender: UserMessageSender
) {
    fun remove(userId: UserId) {
        val user = userRepository.findById(userId.value) ?: throw CustomUserException.UserNotFoundException
        userRepository.delete(user)
        redisTokenRepository.removeUser(user.id)
        userMessageSender.send(DeleteUserEvent.from(user))
    }
}

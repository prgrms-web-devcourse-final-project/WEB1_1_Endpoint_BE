package com.grepp.quizy.user.domain.user

import org.springframework.stereotype.Component

@Component
class UserAppender(
    private val userRepository: UserRepository,
    private val userMessageSender: UserMessageSender,
    private val redisTokenRepository: RedisTokenRepository
) {
    fun append(user: User): User {
        userMessageSender.send(CreateUserEvent.from(user))
        redisTokenRepository.saveUser(user.id)
        return userRepository.save(user)
    }
}

package com.grepp.quizy.user.domain.user

import org.springframework.stereotype.Component

@Component
class UserAppender(
    private val userRepository: UserRepository,
    private val userMessageSender: UserMessageSender,
    private val redisTokenRepository: RedisTokenRepository
) {
    fun append(user: User): User {
        val newUser = userRepository.save(user)
        userMessageSender.send(CreateUserEvent.from(newUser))
        redisTokenRepository.saveUser(newUser.id)
        return newUser
    }
}

package com.grepp.quizy.user.domain.user

import org.springframework.stereotype.Component

@Component
class UserAppender(
    private val userRepository: UserRepository,
    private val userMessageSender: UserMessageSender
) {
    fun append(user: User): User {
        userMessageSender.send(CreateUserEvent.from(user))
        return userRepository.save(user)
    }
}

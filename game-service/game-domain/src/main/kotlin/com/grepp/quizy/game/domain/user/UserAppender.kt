package com.grepp.quizy.game.domain.user

import org.springframework.stereotype.Component

@Component
class UserAppender(
    private val userRepository: UserRepository
) {
    fun append(user: User): User {
        return userRepository.save(user)
    }
}
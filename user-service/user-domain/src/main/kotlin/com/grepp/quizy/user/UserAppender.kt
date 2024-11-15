package com.grepp.quizy.user

import org.springframework.stereotype.Component

@Component
class UserAppender (
        private val userRepository: UserRepository
) {
    fun appendUser(user: User): User {
        return userRepository.save(user)
    }
}
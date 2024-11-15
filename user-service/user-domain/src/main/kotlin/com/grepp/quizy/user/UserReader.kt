package com.grepp.quizy.user

import org.springframework.stereotype.Component

@Component
class UserReader (
        private val userRepository: UserRepository
) {
    fun getUserById(userId: Long): User {
        return userRepository.findById(userId)
    }

}
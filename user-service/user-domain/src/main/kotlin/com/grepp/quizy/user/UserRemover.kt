package com.grepp.quizy.user

import org.springframework.stereotype.Component

@Component
class UserRemover (
    private val userRepository: UserRepository
) {
    fun removeUser(user: User) {
        userRepository.delete(user)
    }
}
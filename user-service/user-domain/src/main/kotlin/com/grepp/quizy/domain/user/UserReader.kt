package com.grepp.quizy.domain.user

import com.grepp.quizy.domain.user.exception.UserNotFoundException
import org.springframework.stereotype.Component

@Component
class UserReader(
    private val userRepository: UserRepository
) {
    fun read(userId: Long): User =
        userRepository.findById(userId) ?: throw UserNotFoundException()

    fun read(email: String): User =
        userRepository.findByEmail(email) ?: throw UserNotFoundException()

    fun isExist(email: String): Boolean =
        userRepository.existsByEmail(email)
}
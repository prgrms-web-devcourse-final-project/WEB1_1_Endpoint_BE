package com.grepp.quizy.user.domain.user

import com.grepp.quizy.user.domain.user.exception.CustomUserException
import org.springframework.stereotype.Component

@Component
class UserReader(
    private val userRepository: UserRepository
) {
    fun read(userId: UserId): User =
        userRepository.findById(userId.value) ?: throw CustomUserException.UserNotFoundException

    fun read(email: String): User =
        userRepository.findByEmail(email) ?: throw CustomUserException.UserNotFoundException

    fun isExist(email: String): Boolean =
        userRepository.existsByEmail(email)
}
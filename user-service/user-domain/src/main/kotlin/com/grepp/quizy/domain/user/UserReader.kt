package com.grepp.quizy.domain.user

import org.springframework.stereotype.Component

@Component
class UserReader(private val userRepository: UserRepository) {
    fun read(userId: Long): User = userRepository.findById(userId)
}

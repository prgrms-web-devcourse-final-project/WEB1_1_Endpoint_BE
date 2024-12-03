package com.grepp.quizy.game.domain.user

import org.springframework.stereotype.Component

@Component
class UserRemover(
    private val userRepository: UserRepository
) {
    fun remove(userId: Long) {
        userRepository.deleteById(userId)
    }
}
package com.grepp.quizy.user.domain.user

import org.springframework.stereotype.Component

@Component
class UserRemover(private val userRepository: UserRepository) {
    fun remove(user: User) = userRepository.delete(user)
}

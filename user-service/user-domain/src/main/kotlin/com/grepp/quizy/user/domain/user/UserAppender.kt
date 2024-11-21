package com.grepp.quizy.user.domain.user

import org.springframework.stereotype.Component

@Component
class UserAppender(private val userRepository: UserRepository) {
    fun append(user: User): User = userRepository.save(user)
}

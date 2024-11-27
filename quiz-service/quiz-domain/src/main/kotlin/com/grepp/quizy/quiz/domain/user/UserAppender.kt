package com.grepp.quizy.quiz.domain.user

import org.springframework.stereotype.Component

@Component
class UserAppender(private val userRepository: UserRepository) {
    fun append(user: User) {
        userRepository.save(user)
    }
}
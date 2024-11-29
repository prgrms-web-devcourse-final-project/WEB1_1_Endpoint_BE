package com.grepp.quizy.quiz.domain.user

import org.springframework.stereotype.Component

@Component
class UserReader(
    private val userRepository: UserRepository
) {

    fun readIn(userIds: List<UserId>): List<User> =
        userRepository.findByIdIn(userIds)
}
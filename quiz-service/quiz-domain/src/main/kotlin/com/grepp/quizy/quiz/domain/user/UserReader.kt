package com.grepp.quizy.quiz.domain.user

import com.grepp.quizy.quiz.domain.user.exception.UserNotFoundException
import org.springframework.stereotype.Component

@Component
class UserReader(
    private val userRepository: UserRepository
) {

    fun read(userId: UserId): User =
        userRepository.findById(userId) ?: throw UserNotFoundException

    fun readIn(userIds: List<UserId>): List<User> =
        userRepository.findByIdIn(userIds)
}
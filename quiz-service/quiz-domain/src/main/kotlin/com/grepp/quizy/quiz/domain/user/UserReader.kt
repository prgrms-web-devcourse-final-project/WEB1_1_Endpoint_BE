package com.grepp.quizy.quiz.domain.user

import com.grepp.quizy.quiz.domain.user.exception.UserException
import org.springframework.stereotype.Component

@Component
class UserReader(
    private val userRepository: UserRepository
) {

    fun read(userId: UserId): User =
        userRepository.findById(userId)
            ?: throw UserException.UserNotFoundException

    fun readIn(userIds: List<UserId>): List<User> =
        userRepository.findByIdIn(userIds)
}
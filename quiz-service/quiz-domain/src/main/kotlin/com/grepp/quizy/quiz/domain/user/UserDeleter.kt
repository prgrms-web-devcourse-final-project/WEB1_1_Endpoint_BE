package com.grepp.quizy.quiz.domain.user

import org.springframework.stereotype.Component

@Component
class UserDeleter(
    private val userReader: UserReader,
    private val userRepository: UserRepository
) {

    fun delete(id: UserId) {
        val user = userReader.read(id)
        userRepository.delete(user)
    }
}
package com.grepp.quizy.quiz.domain.user

import org.springframework.stereotype.Component

@Component
class UserUpdater(
    private val userReader: UserReader,
    private val userRepository: UserRepository
) {

    fun update(userForUpdate: User) {
        val user = userReader.read(userForUpdate.id)
        user.update(userForUpdate.name, userForUpdate.imgPath)
        userRepository.save(user)
    }
}
package com.grepp.quizy.game.domain.user

import com.grepp.quizy.game.domain.exception.UserException
import org.springframework.stereotype.Component

@Component
class UserUpdater(
    private val userRepository: UserRepository
) {
    fun update(user: User): User {
        val oldUser = userRepository.findById(user.id)
            ?: throw UserException.UserNotFoundException
        oldUser.updateUserInfo(user.name, user.imgPath)
        return userRepository.save(oldUser)
    }
}
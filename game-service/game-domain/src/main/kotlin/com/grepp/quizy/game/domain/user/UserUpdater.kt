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

    fun updateRating(id: Long, rating: Int) {
        val user = userRepository.findById(id)
            ?: throw UserException.UserNotFoundException
        user.updateRating(rating)
        userRepository.save(user)
    }

}
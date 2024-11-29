package com.grepp.quizy.game.domain.user

import com.grepp.quizy.game.domain.exception.UserException
import org.springframework.stereotype.Component

@Component
class UserReader(
    private val userRepository: UserRepository
) {

    fun read(userId: Long): User =
        userRepository.findById(userId)
            ?: throw UserException.UserNotFoundException

    fun readIn(userIds: List<Long>): List<User> =
        userRepository.findByIdIn(userIds)
            .also { users ->
                require(userIds.size == users.size) {
                    throw UserException.UserNotFoundException
                }
            }

    fun readRating(userId: Long): Int =
        userRepository.findRatingById(userId)
            ?: throw UserException.UserNotFoundException

}
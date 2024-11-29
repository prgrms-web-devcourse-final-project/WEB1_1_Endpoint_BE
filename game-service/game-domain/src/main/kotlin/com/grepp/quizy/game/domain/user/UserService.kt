package com.grepp.quizy.game.domain.user

import org.springframework.stereotype.Service

@Service
class UserService(
    private val userReader: UserReader
) {

    fun getUserRating(userId: Long) =
        userReader.readRating(userId)

}
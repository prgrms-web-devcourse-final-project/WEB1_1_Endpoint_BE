package com.grepp.quizy.domain.user

import org.springframework.stereotype.Service

@Service
class UserService (
    private val userAppender: UserAppender,
    private val userReader: UserReader,
    private val userRemover: UserRemover
) {
    fun appendUser(user: User) {
        userAppender.appendUser(user)
    }

    fun getUser(userId: Long): User {
        return userReader.getUserById(userId)
    }

    fun removeUser(user: User) {
        userRemover.removeUser(user)
    }
}
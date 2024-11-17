package com.grepp.quizy.domain.user

import org.springframework.stereotype.Service

@Service
class UserService (
    private val userAppender: UserAppender,
    private val userReader: UserReader,
    private val userRemover: UserRemover
) {
    fun appendUser(user: User): User {
        return userAppender.append(user)
    }

    fun getUser(userId: Long): User {
        return userReader.read(userId)
    }

    fun removeUser(user: User) {
        userRemover.remove(user)
    }
}
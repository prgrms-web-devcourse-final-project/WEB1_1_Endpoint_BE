package com.grepp.quizy.domain.user

import org.springframework.stereotype.Service

@Service
class UserService(
    private val userAppender: UserAppender,
    private val userReader: UserReader,
    private val userRemover: UserRemover
) : UserCreateUseCase, UserReadUseCase, UserDeleteUseCase {
    override fun appendUser(user: User): User {
        return userAppender.append(user)
    }

    override fun getUser(userId: Long): User {
        return userReader.read(userId)
    }

    override fun removeUser(user: User) {
        userRemover.remove(user)
    }
}
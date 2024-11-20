package com.grepp.quizy.user.domain.user

interface UserCreateUseCase {
    fun appendUser(user: User): User
}
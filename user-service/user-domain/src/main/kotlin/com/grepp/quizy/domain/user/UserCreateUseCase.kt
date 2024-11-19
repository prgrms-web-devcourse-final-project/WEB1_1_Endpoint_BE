package com.grepp.quizy.domain.user

interface UserCreateUseCase {
    fun appendUser(user: User): User
}
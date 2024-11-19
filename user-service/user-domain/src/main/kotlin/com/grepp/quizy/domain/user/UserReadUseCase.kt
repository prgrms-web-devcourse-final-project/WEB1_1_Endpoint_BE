package com.grepp.quizy.domain.user

interface UserReadUseCase {
    fun getUser(userId: Long): User
}